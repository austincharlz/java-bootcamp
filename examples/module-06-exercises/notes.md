#### Exercise 5 Notes
- map produced a new list of proposed values; it did not modify the immutable Employee records in the source list.
#### Exercise 6 Notes
1. Why is the value type `Long`, not `Integer`?
   * Collector uses Long.
2. What would the values contain if you removed `Collectors.counting()`?
   * They would contain lists of the original stream elements.
3. Why is a `TreeMap` used only for presentation here?
   * The original did not need sorted keys.
#### Exercise 7 Notes
-  filter: Stream<Employee> -> Stream<Employee>
-  map:    Stream<Employee> -> Stream<String>
-  sorted: Stream<String>   -> Stream<String>
-  toList: Stream<String>   -> List<String>
#### Exercise 8 Notes
- Shared mutable counter lose updates under concurrent workers. You should build-in count().