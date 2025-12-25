When a client adds an expense, the request goes to ExpenseController, which delegates to ExpenseService.
Inside the service, I build an ExpenseContext, use a Strategy pattern via SplitStrategyFactory
to get the correct split implementation (equal/exact/percent), and compute ExpenseSplits.

Then I persist the Expense via ExpenseRepository and call BalanceService.updateBalancesForExpense.
BalanceService updates group-level balances and user-to-user balances through their repositories,
using locks per balance key to stay thread-safe. The controller then returns the created expense ID to the client.

In a real system, all of this is wrapped in a transaction (e.g., via a simple TransactionManager in plain Java

client adds an expense -->
a. ExpenseController -> ExpenseService -> ExpenseContext -> Strategy pattern via SplitStrategyFactory -> persist the
Expense via ExpenseRepository
b. call BalanceService.updateBalancesForExpense ->
(1. updates group-level balances 2. user-to-user balances through their repositories)
returns expense ID to the client.

### ✅ 1. What does ReentrantLock protect?

ReentrantLock protects in-memory operations inside your Java process, not the database itself.
It prevents two threads inside your application from updating the same balance at the same time.

This matters because:

Even if you use a DB later, Or even if repo is in-memory (your LLD),
Your BalanceService must still be thread-safe.

#### **🔥 Key point: ReentrantLock ≠ Database Lock.**

* It is not a real DB lock.
* It prevents concurrent updates in the application layer, which is still important.
* If you later add a real DB (MySQL/PostgreSQL), then:
* App-level ReentrantLock protects Java memory state
* DB locking (SELECT FOR UPDATE, row-level lock) protects database state

In interviews, this shows:

“I understand both application-level concurrency and DB-level concurrency.”

### ✅ 2. Why do we need TWO locks (fromUser and toUser)?

Because two different balance rows are being modified:

In settlement:
`GroupBalance(groupId, fromUserId)  → needs lock
GroupBalance(groupId, toUserId)    → needs lock`

We do two updates:

`Increase fromUser’s balance
Decrease toUser’s balance`

If you don’t lock both rows, the following race conditions can occur:

😱 Scenario Without 2 Locks

`Thread T1: From Abh → To Riya (₹1000)
Thread T2: From Riya → To Abh (₹200)`

Both update:

FromUserBalance
ToUserBalance

WITHOUT locking:

* Final values may override each other
* Lost update anomalies
* Half updates (only one user updated)
* Stale reads

This creates completely wrong balances.

### 🌟 3. So why exactly 2 locks?

Because each balance row must be protected individually.

`"GB-<groupId>-<fromUserId>" protects From user row
"GB-<groupId>-<toUserId>" protects To user row`

Example:

`GB-10-101 → Balance of user 101 in group 10
GB-10-102 → Balance of user 102 in group 10`

If two threads touch the same pair simultaneously, locks ensure:

✔ No conflicting writes
✔ No partial updates
✔ No lost updates
✔ Correct atomicity over both rows

### 🧠 4. But locking two resources can cause deadlock — aren’t you worried?

Excellent question again.

Yes — if two threads lock in different order, they can deadlock.

Example bad order:

`T1 locks A then B
T2 locks B then A  → DEADLOCK`

🔥 Our implementation avoids this by always locking in the SAME order.

We lock using:

`key = "GB-" + groupId + "-" + userId`

Since locks are always acquired as:

`fromLock.lock();
toLock.lock();`

And:
fromUser and toUser are always deterministic per request
We always acquire in the same sequence for same pair

Thus:
✔ no two threads will lock the same pair in reverse order
✔ deadlock is avoided

(This is one of the cleanest tricks to avoid deadlock in application-level locking.)

### 🧩 5. Do I need application locks if using actual DB?

YES — if doing more than one DB write per request.
NO — if using DB transactions on each row read/write with row-level locks.

In real systems: Options:

#### 1️⃣ Application locks only (in-memory)

* Works only in single-node apps
* Fails in distributed system

#### 2️⃣ Database locks only (SELECT FOR UPDATE)

* Works across multiple app nodes
* Avoids race conditions in distributed systems

#### 3️⃣ Both

* Protects both DB and in-memory caches
* You typically don’t need both unless using a complex memory+db hybrid system

### 🎯 Final interview-ready explanation

“The ReentrantLocks protect application-level concurrency so that no two threads in the JVM update the same balance
simultaneously.
They do NOT replace DB locks.
In a real system I would also use row-level locking (SELECT FOR UPDATE) or optimistic locking at the DB layer.
We need two locks — one for fromUser and one for toUser — because we modify two different balance rows atomically.
Locking both ensures correctness and prevents lost updates.”

# ⭐ ReentrantLock = PESSIMISTIC LOCKING

* Not optimistic.
* Not hybrid.
* Not DB-level.
* 100% pessimistic locking at application level.

### ✅ What is Pessimistic Lock?

A pessimistic lock assumes:

_“A conflict WILL happen, so block other threads before doing any work.”_

So the thread:

`Takes a lock -> Blocks all other threads -> Performs the update -> Unlocks`

This is exactly how ReentrantLock works.

🧠 How your ReentrantLock behaves in your Splitwise design

Example:

ReentrantLock l = BalanceLockManager.getLock("GB-1-101");
l.lock();
// update balance row
l.unlock();


This means:

* Only ONE thread can update that particular balance row at a time
* All other threads trying to lock "GB-1-101" will STOP and WAIT
* This is blocking, not retry-based

Therefore: pessimistic.

### 🆚 Optimistic vs Pessimistic Memory Locks

#### ✔ Pessimistic

* Acquires lock first
* Blocks others
* No conflicts because no parallel edits
* Used when conflicts are likely
* This is ReentrantLock

#### ✔ Optimistic

* Does NOT lock
* Reads the data
* Tries to update
* Checks a version

If version changed → retry again

* Used when conflicts are rare
* This is typically done via:
* CAS (Compare-And-Set)
* Atomic classes
* @Version in JPA/Hibernate (DB optimistic locking)

Example optimistic flow:

read version = 5
update balance...
set version = 6 only if DB version == 5
if fail → retry

🔥 Which one do WE use in Splitwise LLD?
👉 ReentrantLock = pessimistic locking at application level
👉 In-memory repository = no built-in optimistic lock
👉 Database is not used in this LLD, so versioning is absent

Therefore:

❌ No version fields
❌ No CAS
❌ No retries
❌ No optimistic locking
✔ YES pessimistic application-level locking

🧑‍🏫 When the interviewer asks:

“Is your locking optimistic or pessimistic?”

Say:

“I’m using application-level ReentrantLock, which is a pessimistic lock — it prevents concurrent modifications by blocking other threads until the current update finishes.
It simulates row-level locking in a DB.
If I were using a real DB, I would use optimistic locking with version fields or pessimistic DB locks using SELECT FOR UPDATE.”

This is a PERFECT explanation.

### 🎯 Bonus: When to use which in real systems?

###### Use Optimistic Locking when:

* Conflicts are RARE
* High read throughput
* E-commerce order inventory
* Banking ledger with retries
* High concurrency

###### Use Pessimistic Locking when:

* Conflicts are LIKELY
* Operations must be SERIALIZED
* Accounting like Splitwise (balances!)
* Payment transactions
* Updating shared counters

Splitwise-like balance computations actually favor pessimistic approach because correctness > throughput.
