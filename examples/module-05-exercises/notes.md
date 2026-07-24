#### Exercise 1 Notes
- After adds:  [Java Fundamentals, Clean Code, Effective Java, Java Fundamentals]
- After set:   [Java Fundamentals, Clean Architecture, Effective Java, Java Fundamentals]
- After remove:[Clean Architecture, Effective Java, Java Fundamentals]
#### Exercise 2 Notes
- Sets determine duplicate using equals and hashCode. Strings already implement them. Lab 5 must define identity carefully when custom objects are stored in sets. 
#### Exercise 3 Notes
- TreeMap keys always iterate in sorted order — that part is guaranteed. HashMap key order is not a contract; it may differ between runs or JDK versions, so never depend on it for display order.
#### Exercise 7 Notes
- The map is updated only after the title was successfully removed from the available list. Updating the map first could record a loan for an unavailable title and leave inconsistent state.