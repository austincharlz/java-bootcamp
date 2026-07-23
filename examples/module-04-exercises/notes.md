#### Exercise 01 Notes
1. Main frame is created.
2. Person is allocated; main.person refers to it which is on the heap.
3. printPerson creates a second frame.
4. Both frames temporarily hold references to the same Person on the heap.
5. printPerson returns; its frame is removed (LIFO).
6. Main returns; frame is removed as well.
#### Exercise 02 Notes
- An object is not collectible merely because one reference becomes null. 
- It becomes GC-eligible only when no live strong-reference path can reach it.
- Eligibility does not guarantee immediate collection, and System.gc() is only a request. 
#### Exercise 03 Notes
- My program allocated just above 250 MB over the time despite having a 64 MB max for the heap.
- GC log entries appeared between the rounds that showed a before and after value, the latter usually being lower because of GC.
- Exact pause times varies from machine to machine
#### Exercise 04 Notes
- Command: java -XX:+UseG1GC -Xms16m -Xmx64m -Xlog:gc GcObserve
- Evidence: The log began with "Using G1" and showed G1 evacuation pauses. The collector flag selects G1; it does not guarantee a particular pause time.
#### Exercise 05 Notes
- Command: java -XX:+UseZGC -Xms16m -Xmx64m -Xlog:gc GcObserve
- Evidence: The log begins with "Using The Z Garbage Collector" instead of "Using G1". Pause-related log lines are different as ZGC does a lot of its work concurrently. This means it does not have the same STW (Stop-The-World) "Evacuation Pause" as G1 does. 
#### Exercise 06 Notes
- loaded RetentionDemo class
  → static CACHE field
  → ArrayList entries
  → byte[] objects
- Root cause: a long-lived static collection retained strong references after the data was no longer needed. GC could not reclaim reachable entries.
- Fix: clear/remove entries, bound the cache, apply eviction, or use a more appropriate lifecycle. Weak references are not a universal cache fix.
#### Exercise 07 Notes
- Use StringBuilder when creating text in a loop. Ordinary "+" remains readable and appropriate for smaller, fixed expressions. 