### **ConcurrentHashMap** 
is thread-safe because it uses internal locks on specific parts of the map (segments or bins) rather than locking the entire map, 
allowing multiple threads to access different parts of the map concurrently. 
This fine-grained locking strategy

## 🔥 TOP 15 CROSS QUESTIONS YOU MUST PREPARE FOR

### 1️⃣ Seat Locking / Concurrency

### 🔹Q1. What happens if two users try to book the same seat at the same time?

* First layer of protection is the SeatLockService, which prevents two threads from locking the same seat simultaneously.
* Second layer is during actual booking — I mark the seat as BOOKED only inside a DB transaction (or using atomic update in the repo).
* Even if two users reach booking at the same millisecond, the DB-level check ensures only one succeeds.
* Application lock avoids high contention, DB lock enforces correctness.

### 🔹 Q2. What if lock expires but user pays after that?

* Booking is only confirmed after validating the seat lock. If the lock expired, validation fails — even if the payment succeeded.
* Then the system triggers a refund flow.
* This ensures seats are never double-booked due to late payment callbacks.

### 🔹 Q3. Why use in-memory lock? What about multiple servers?

* In-memory locks work only for a single server.
* In production, we replace the lock implementation with a Redis-based distributed lock.
* I already have an interface: SeatLockService.
* So swapping the implementation requires zero changes in business logic.

### 2️⃣ Repository / Data Modeling
### 🔹 Q4. Why separate ShowSeat from Seat?

* Seat is static — it belongs to the screen and never changes.
* But seat status changes for every show.
* The same physical seat can be:
    ** Available in the 6 PM show
    ** Booked in the 9 PM show
* So status must be modeled against a specific show → ShowSeat.

### 🔹 Q6. Why no database transactions in your design?

* In-memory version doesn’t need it
* In real system → use DB transactions + “select for update” for row locking
* Mention atomic operation in booking step.

### 3️⃣ OO Design & Architecture
### 🔹 Q7. Why did you convert repositories into interfaces?

* It decouples the business logic from the data store.
* Tomorrow the storage may change from in-memory → MySQL → Redis → microservice.
* Since the service layer depends only on interfaces, I can replace implementations without touching any business logic.
* This makes the system maintainable and testable.

### 🔹 Q8. Why is Ticket a separate class from Booking?

Ticket may have:

* QR code
* Expiration
* Entry validation
* These concerns should not pollute the Booking entity.

### 🔹 Q9. How would you add payments into your design?

* Strategy pattern
* PaymentStrategy (UPI, card, wallet)
* PaymentService orchestrates
* BookingService waits for success callback.

### 4️⃣ Scalability / HLD Cross Questions
### 🔹 Q10. How will you scale your system if you have millions of users booking simultaneously?

##### “Search path is read-heavy, so I cache all static data:

* Movies
* Cinemas
* Screens

##### Booking path is write-heavy.

* So I scale that by:
* Redis-based seat locks (distributed).
* Horizontal scale of booking service (stateless).
* Database sharding by showId or city.
* Use asynchronous payment flow.
* CDN for posters

This ensures only the hot path — seat booking — requires strict consistency.”


### 🔹 Q11. What happens if the SeatLockService server goes down?

* Redis-based lock solution solves this
* Locks stored in distributed memory
* Stateless booking service.

### 🔹 Q12. What if payment succeeds but booking fails?

This is a classic distributed transaction problem.

I solve it using:

Outbox Pattern or
Refund flow

Payment is idempotent and booking is idempotent using bookingId as an idempotency key.
System retries booking OR triggers refund if booking still fails.

### 5️⃣ Edge Cases & Reliability
### 🔹 Q13. Can one user lock 100 seats intentionally?

* I add rate-limiting at API gateway level.
* Also I enforce a seat lock quota per user or per session.
* Locks expire automatically, so seats don’t remain blocked.


### 🔹 Q15. What if two shows end and start within 1 minute on same screen?


### ⭐ BONUS — INTERVIEWER "TRICK" QUESTIONS

These are used to check if you really understand LLD:

### 🔹 Trick Q1. Why do we even need ShowSeat status in memory when database should handle it?

Expected:

* DB-only locking is slow and increases contention.
* In-memory lock is the fast optimistic layer,
* DB lock is final validation layer.
* Faster UI response.

### Trick Q2. How do you avoid double-booking on payments?

“Booking is not confirmed on payment success.
Booking is confirmed only after verifying the lock and marking seats BOOKED atomically.
This prevents double-booking.

### 🔹 Trick Q3. How do you ensure idempotency in BookingService?

* Use bookingId as idempotency key
* If same bookingId comes again → return cached result.

# What if payment succeeds but booking fails?”

This problem appears in systems like BookMyShow, airline booking, hotel booking, etc.

It is a distributed transaction problem:

Payment Service runs in one system.

Booking Service / Inventory Service runs in another system.

Payment may succeed but seat reservation may fail (race condition, DB issue, timeout, partial failure, etc.).

So we need a way to make the system eventually consistent.

⭐ What is the Outbox Pattern? (Clean explanation)

Outbox Pattern ensures reliable communication between services when one service updates its DB but needs to send an event to another service.

How it works:

Booking service inserts/update booking record and writes an “event” into a local OUTBOX table in the same DB transaction.
Example outbox row:

{ eventType: BOOKING_CREATED, bookingId: 123, userId: 5 }


A background Outbox Processor (or Debezium / CDC) reads the outbox table and publishes events to Kafka/RabbitMQ.

Other services consume these events reliably.

Why it solves problems?

Because:

DB write + outbox insert happen in one atomic transaction.

Even if the service crashes, the event is not lost.

Prevents “payment succeeded but no booking event sent”.

⭐ How it applies to the booking-payment case
Case:

Payment succeeds

Booking fails (due to seat not available / DB timeout / network issue)

Using Outbox Pattern:

Payment Service writes a PAYMENT_SUCCESS event into its outbox table.

Booking Service consumes this event and attempts seat allocation.

If booking fails → it writes BOOKING_FAILED into outbox.

Payment Service listens to this and auto-triggers refund.

Thus no money is lost.

⭐ Idempotency: Why it's mentioned?

To avoid duplicates when retry happens.

1. Payment idempotency

Payment gateway may send the callback multiple times.

You use a unique paymentId / bookingId so the payment is processed only once.

2. Booking idempotency

Booking service may receive the same request twice.

Using bookingId as idempotency key ensures:

Seat booking is tried once

Duplicate requests return the same result

⭐ In one crisp line for interview:

“Outbox pattern ensures reliable event delivery between services.
Payment → Outbox → Event → Booking → Outbox → Event → Refund.
Even if any system goes down, the event is not lost, and system becomes eventually consistent.”

⭐ Final Interview-Ready Answer (verbatim)

“
This is a classic distributed transaction problem because payment and booking are two different services.

I handle this using the Outbox Pattern + Idempotent Operations.

When payment succeeds, the Payment Service writes a PAYMENT_SUCCESS event into an outbox table in the same DB transaction.

Booking Service consumes it and tries seat allocation idempotently.

If booking still fails, Booking Service writes a BOOKING_FAILED event to its outbox.

Payment Service consumes that and triggers a refund.

Both payment and booking are idempotent using bookingId as the idempotency key.

This ensures eventual consistency, no double booking, and no money loss even if services fail."
”