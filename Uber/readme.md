1️⃣ Concurrency Deep Dive – Race Conditions (Cab Booking Context)
🔴 Problem Scenario (Classic Uber Bug)

Two riders request a cab at the same time.

Thread T1 → check driver available → YES
Thread T2 → check driver available → YES
T1 assigns driver
T2 assigns SAME driver ❌

❌ Why this happens

if (available) { available = false; } is NOT atomic

Context switch happens between check & update

✅ Correct Fix #1: AtomicBoolean (Best in Core Java)
class Driver {
private final AtomicBoolean available = new AtomicBoolean(true);

    public boolean reserve() {
        return available.compareAndSet(true, false);
    }

    public void release() {
        available.set(true);
    }
}

#### ✔ Why this works

compareAndSet() is lock-free + atomic

Only one thread can reserve the driver

💬 Say in interview:

I use CAS via AtomicBoolean instead of synchronized to avoid thread contention and improve scalability.

#### ✅ Fix #2: Synchronized Block (Acceptable, Less Scalable)
`synchronized(driver) {
 if (driver.isAvailable()) {
   driver.setAvailable(false);
 }}`


❌ Blocks threads
❌ Poor scalability

#### ✅ Fix #3: Optimistic Locking (DB level – real systems)
UPDATE driver
SET available = false
WHERE id = ? AND available = true;

* Rows affected = 1 → success
* Rows affected = 0 → already taken

💬 Production-level answer

2️⃣ Redis-Based Cab Locking Design (Highly Impressive)

This is what Uber / Ola actually do

🧠 Why Redis?

* Low latency
* Distributed lock
* Works across multiple servers

🔒 Lock Key Design
LOCK:DRIVER:{driverId}

🔑 Lock Acquisition (Atomic in Redis)
SET LOCK:DRIVER:123 server1 NX EX 30


NX → only if not exists

EX 30 → auto release after 30 sec (crash safety)

Java Pseudo Code
```
public boolean lockDriver(String driverId) {
   String key = "LOCK:DRIVER:" + driverId;
   return redis.set(key, "SERVER_ID", NX, EX, 30);
}
```

🟢 Booking Flow with Redis Lock
1. Fetch nearby drivers
2. Try lock one-by-one
3. First lock success → assign driver
4. Booking confirmed
5. Unlock after trip end

🔓 Unlock (Safe Way)
if redis.get(key) == SERVER_ID
then redis.del(key)


✔ Prevents deleting others’ lock

💬 Say This in Interview:

In a distributed system, in-memory locks fail. I use Redis distributed locks with TTL to prevent double booking and handle server crashes.

🔥 Instant Senior Signal

3️⃣ How to Answer Follow-up Questions Confidently
🔑 GOLDEN RULE

Never jump into code immediately

### “What if two requests come at same time?”

_**1️⃣ Acknowledge issue**_
This causes a race condition where the same driver might get assigned twice.

_**2️⃣ Explain impact**_
That breaks core business logic and user trust.

_**3️⃣ Give solution options**_
In-memory CAS, DB locking, Redis distributed lock.

_**4️⃣ Choose best based on scale**_
* For single instance → AtomicBoolean
* For distributed → Redis / DB optimistic locking

❓ “Is synchronized enough?”

❌ Bad:
Yes, we can synchronize the method.

✅ Good:
Synchronized works only in single JVM. In multi-node deployments, I’d use Redis or DB-level locking.

❓ “How will you scale driver matching?”
I’d separate driver-matching into its own service, use geo-hashing for proximity search, and cache driver locations in Redis.

❓ “What happens if booking service crashes?”
Redis lock TTL ensures auto-release. Booking status stays in DB and can be recovered using event replay.

## Why We Need Spatial Indexing (Context)

Problem:

“Find nearest available drivers within X km in milliseconds”

❌ Scanning DB → O(n) (doesn’t scale)
✅ Use spatial partitioning

Two popular approaches:
1️⃣ QuadTree
2️⃣ GeoHash

1️⃣ QuadTree (Conceptual, Tree-Based)
🧠 Intuition

Start with whole map
Divide into 4 quadrants
Keep splitting until each cell has limited points (drivers)
World
└── India
└── City
└── Area
└── Block

⚙️ How QuadTree Works

Insert driver location

If node > capacity → split into 4
For search → traverse only relevant nodes

Time Complexity
Insert: O(log n), Search nearby: O(log n)

🚕 QuadTree in Cab Booking
Root
├── North-West (drivers)
├── North-East
├── South-West
└── South-East

Used in in-memory matching services

Best when:

Highly dynamic locations
Custom geo logic required

#### ⚠️ Drawbacks

* ❌ Complex to implement
* ❌ Hard to distribute across nodes
* ❌ Re-balancing overhead

💬 Interview tip:
QuadTree is powerful but operationally complex at scale.

2️⃣ GeoHash (Industry Favorite)

🧠 Intuition

Convert latitude & longitude → string
Lat/Lon → "tdr5x"

Nearby locations → similar prefixes
tdr5x
tdr5y
tdr5z

📏 Precision Control
_* Prefix Length	  Area Size_
*      4	      ~20 km
*      5	      ~2.4 km
*      6	      ~600 m
*      7	      ~150 m

#### 🚕 GeoHash in Cab Booking
* Data Storage
* Redis Key: GEO:tdr5x
* Value: driverId

#### _Query Flow_

* Compute GeoHash of pickup
* Fetch drivers in same prefix
* Also check 8 neighboring hashes
* Rank by distance

#### 🚀 Why Uber/Ola Prefer GeoHash

✔ Simple
✔ Works with Redis
✔ Easy to shard
✔ Prefix-based search
✔ Horizontal scaling

#### 💬 Say this:

GeoHash is string-based, which makes it highly suitable for distributed systems and Redis.

### ⚠️ GeoHash Drawbacks

* ❌ Border issues (edge of cells)
* ❌ Requires neighbor lookup
* ❌ Not exact distance → needs refinement

Race conditions happen when shared mutable state is accessed without atomicity.
I fix this using CAS in JVM and Redis/DB locking in distributed systems.

2️⃣ Do You Know Distributed Locks vs JVM Locks?
JVM Locks (synchronized / ReentrantLock)

_**✔ Pros**_
* Simple
* Good for single JVM

_**❌ Cons**_
* Breaks in multi-instance deployments
* No protection across services

💬 Say:
JVM locks do not work across instances.

Distributed Locks (Redis / ZooKeeper)
_**✔ Pros**_
* Works across machines
* Ensures global consistency
* TTL handles crashes

_**❌ Cons**_
* Network latency
* Needs careful unlock logic

### 4️⃣ Do You Handle Partial Failures?

This question separates mid-level vs senior engineers.

**_Failure 1_**: Service crashes after locking driver

* ✔ Redis TTL auto-releases lock
* ✔ No permanent blocking

_**Failure 2**_: DB commit succeeds but response fails

* ✔ Booking exists in DB
* ✔ Client retries with bookingId (idempotency)

**_Failure 3_**: Redis fails but DB is up

* ✔ Fallback to DB optimistic lock
* ✔ Slight latency increase but correctness maintained

I handle race conditions using CAS in JVM and Redis/DB locking in distributed systems.
I use GeoHash for scalable proximity search instead of QuadTree due to operational simplicity.
Partial failures are handled using TTL, idempotency, and optimistic locking.
The design scales horizontally by externalizing state and coordination.