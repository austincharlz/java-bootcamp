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