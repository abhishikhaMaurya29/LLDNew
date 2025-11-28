“Modern servers do not create a new thread per request; they use a limited thread pool.
If we let the server thread execute the game logic directly, long-running turns would block these threads and cause thread starvation under load.

So we decouple request handling from turn execution.
The server thread simply queues a turn request, and a fixed-size internal executor processes the turns.

This guarantees:

* controlled concurrency
* sequential turn execution
* avoidance of server thread blocking
* stability even under 10,000 simultaneous users
* This is the same principle used in reactive systems like Akka, Kafka, or actor-based models.”