#### Step 10 Results
- | Objects | Used Memory (approx) | Execution Time |
  | ------- |----------------------|----------------|
  | 10 | 0                    | 0              |
  | 100 |  482760                    | 0              |
  | 1,000 |      486360                   | 0              |
  | 100,000 |           10968184            | 3              |
  | 1,000,000 |            112238728            | 50             |
#### Reflection Questions
1. Stack vs Heap?
   * The stack stores method calls and local variables, while the heap stores dynamically created objects.   
2. Why locals on the Stack?
   * Locals are short-lived and should only be used for the method's execution.
3. Why objects on the Heap?
   * Objects are usually accessed by many references and would need a longer lifespan.
4. When is an object GC-eligible?
   * When it no longer has a strong reference to it.
5. Does `System.gc()` guarantee collection?
   * No, it only requests for it, but it can be ignored by the JVM.
6. What caused the leak?
   * It was caused by keeping unused objects in a static list that was not cleared. 
7. How did clearing the list fix it?
   * It removed the references to the objects, so that the garbage collector can collect them. 
8. Why are WeakReferences useful?
   * They allow objects to be garbage collected when they have no strong references anymore. 
9. What happens when the heap is exhausted?
   * The JVM can through an OOM exception if it cannot allocate more memory. 
10. Which laptop tool would you try first for rising heap—and why?
    * VisualVM would be useful because it shows heap usage and object growth. 
11. How could a CRM unbounded cache repeat this leak?
    * It would leak by storing unlimited customer data without removing old or unused objects.
#### Additional Notes
- Leak vs Fix
  - A memory leak is when a program unintentionally keeps references to objects that are no longer needed. This prevents the garbage collector from freeing up that memory.
    A fix removes the unnecessary references when objects are no longer needed. This allows the garbage collector reclaim their memory. 