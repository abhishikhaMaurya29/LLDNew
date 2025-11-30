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
