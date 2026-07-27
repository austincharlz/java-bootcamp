#### Reference Commands (PowerShell)
- Compile and Run
```bash
cd ~/java-bootcamp/examples/Lab6-EmployeeAnalytics
javac -d out src/com/academy/analytics/*.java
java -cp out com.academy.analytics.Main
```
- Clean Rebuild
```bash
cd "$HOME/java-bootcamp/examples/Lab6-EmployeeAnalytics"
Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
javac -d out src/com/academy/analytics/*.java
Get-ChildItem out -Recurse -File
```
- Security and Cleanup
```bash
cd ~/java-bootcamp/examples/Lab6-EmployeeAnalytics
Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
```
#### Concepts to Discuss
1. Why do stream pipelines postpone work until a **terminal** operation runs?
   * Java Streams use lazy evaluation meaning that they do not do any work until a terminal operation runs. It just builds the scaffolding on what to run later.
2. When is a lambda clearer than a named method and when should you extract a method instead?
   * Use lambdas when the behavior is short and only used once and extract a method when the behavior is more complicated or needs to be reused. 
3. What is the difference between `filter()` (same type, fewer elements) and `map()` (possibly different type, same count)?
   * filter() is used to keep or remove elements based on a condition while map() transforms each element into something else. 
4. Why prefer `Comparator.comparingDouble(Employee::getSalary).reversed()` over a handwritten `compare` for salary descending?
   * It is easier to read, more concise, and easier to maintain rather than doing it manually.
5. What does `Collectors.groupingBy(Employee::getDepartment)` give you that a single `List` does not?
   * It gives you a Map of groups instead of just one long collection of employees. It provides more context/data.
6. Why return `Optional<Employee>` from “highest paid” instead of a nullable `Employee`?
   * It forces users to handle cases in their code like the employee not existing. It also prevents bugs related to null. 
7. When should you use a method reference (`Employee::getName`) vs a lambda (`e -> e.getName()`)?
   * Use a method reference when you do not need to filter the data explicitly. Use lambdas when logic must be added to it. 
8. How will CRM later reuse filter/map/group thinking for customers (without claiming CRM is done today)?
   * CRM systems also process collections of objects. The CRM is not built now, but the same patterns apply. 
#### Implementation Checkpoints
- Checkpoint A
  - Pass
  - Pass
  - Pass
- Checkpoint B
  - Pass
  - Pass
  - Pass
  - Pass
- Checkpoint C
  - Pass
  - Pass
  - Pass
  - Pass
- Checkpoint D
  - Pass
  - Pass
  - Pass
  - Pass
#### Manual Verification
1. Menu 1–9 appears; invalid `abc` → invalid message → menu returns.
   * Pass
2. Choice `1` lists all employees; total matches seed size (25 in solution data).
   * Pass
3. Choice `2` prints each department with employee names indented.
   * Pass
4. Choice `3` shows reductions, summarizing stats, and true/false salary partition.
   * Pass
5. Choice `4` lists performers with rating ≥ 4.
   * Pass
6. Choice `5` prints highest paid via Optional path (John Smith with solution seed).
   * Pass
7. Choice `6` prints per-department count / avg / max / min.
   * Pass
8. Choice `7` lists only active employees
   * Pass
9. Choice `8` dashboard matches sample shape (employees, salaries, top 5, active/inactive).
   * Pass
10. Choice `9` prints `Thank You`; recompile after edits to avoid stale `.class` files.
    * Pass
#### Reflection Questions
1. What are the advantages of Streams over loops?
   * Streams make code shorter and easier to read by focusing on what to do instead of how to do it. There are built-in operations, like filterd() and sorted(), that reduce boilerplate code. They allow us to get rid of manual loops and temporary variables.
2. When should Streams be preferred?
   * Streams are preferred when processing collections by filtering, transforming, or any one of the built-in operations. They can be chained together to form a pipeline that is later executed by the terminal operation. Simple tasks that require basic iteration may be easier with regular loops, however. 
3. What is the difference between `filter()` and `map()`?
   * filter() removes certain items to do not match the criteria, while map() transforms each item into something else. For example, filter() was used to keep only active employees or higher earners, while map() was used to extract names, salaries, or departments. One selects data, and the other changes it.
4. Why is `reduce()` useful?
   * reduce() combines all of the elements of stream into one, concise result. Here, it was used to find the highest and lowest salary with Double::max and Double::min. It combines many values into a single answer. 
5. What does `Collectors.groupingBy()` do?
   * It organizes stream elements into groups based on the given key. You are able to display employees based on their department in the lab as an example.
6. What is the benefit of using `Optional`?
   * Optional is the preferred and safe way to handle values that might not exist. Instead of getting null exceptions, we explicitly make the programmer handle those cases. This makes the code more reliable and easier to understand. 
7. Why are Lambda Expressions more readable?
   * They reduce the amount of code needed to get job done. They let you write small pieces of logic in one line that is very human-readable and keeps the amount of classes and methods down. The code is more localized making it easier to maintain in the long run.
8. When should method references be used?
   * Use them a lambda only calls an existing method. They make the code shorter and cleaner without changing its behavior. 
9. Which stream operation is terminal? Give three examples from your lab.
   * A terminal operation finishes the stream pipeline and produces the result or performs an action. In this lab, we used forEach(), count(), and collect() to print employees, count employees, and build grouped maps, respectively. Others include: reduce() and max().
10. How do Streams improve enterprise Java applications?
    * They help process large collections of data in a clear and consistent way. It makes the logic easier to read, maintain, and extend in the future by using reusable operations like filtering and summarizing. Manual loops are more prone to errors and other issues. 
11. (Forward look) How would a future CRM use `filter` / `map` / `groupingBy` on customers the same way this lab uses them on employees—without claiming the CRM is implemented today?
    * Future CRMs could use filter() to find customers who meet certain conditions, like recent purchases. map() could be used to extract names, emails, and addresses for reports on customer data. groupingBy() could be used to organize customers by their region, sales rep., account type, etc. 