#### Reference Commands (PowerShell)
- Compile and Run
```bash
cd "$HOME/java-bootcamp/examples/Lab7-ATMSystem"
javac -d out src\com\academy\atm\*.java
java -cp out com.academy.atm.Main
```
- Get Logs
```bash
New-Item -ItemType Directory -Path logs -Force | Out-Null
Get-Content logs\application.log -Tail 50
(Select-String -Path logs\application.log -Pattern "ERROR").Count
```
- Clean Rebuild
```bash
cd "$HOME/java-bootcamp/examples/Lab7-ATMSystem"
Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
javac -d out src\com\academy\atm\*.java
Get-ChildItem -Recurse out -File
```
- Security and Cleanup
```bash
cd ~/java-bootcamp/examples/Lab7-ATMSystem
Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
```
#### Concepts to Discuss
1. Why are `InvalidAmountException` and friends **checked** in this lab, while `NullPointerException` is unchecked?
   - Because those custom exceptions that must be caught or declared while NullPointerException mostly occur due to programing mistakes and happen at runtime.  
2. What does `throws` on `Account.withdraw(...)` force callers to do?
   - It forces them handle the call and make sure the balance is within the given parameters. It is a custom exception handler.  
3. Why catch specific exceptions before a broad `catch (Exception ex)`?
   - Catching specific exceptions allows you stop the propagation and find the root source of the issue. Also, Java checks the catch blocks top to bottom, so if that comes first, it will catch every exception. This makes the later catch blocks unreachable.
4. What guarantee does `finally` give you that `catch` alone does not?
   - It guarantees that block is executed whether an exception is handled or not. If there is an exception thrown in the try block, the catch block may never run. 
5. Why prefer try-with-resources over `reader.close()` in a `finally` block?
   - try-with-resources does it automatically. This minimizes bugs or issues caused by forgetting to close a reader, scanner, etc. 
6. Why log stack traces to a file while showing short messages to the ATM user?
   - It is important to do this to keep track of failure points. Additionally, it provides context to the user as well, so they are aware of the issue and may address it. 
7. Where should validation throw—deep in `Account` or only in `Main`? Why?
   - It should throw in Account because that is probably closer to the where the root issue is. Having it throw in main does not provide nearly as much context to solve the issue.
8. How will CRM later reuse “domain exception + boundary catch + log” (without claiming CRM is done today)?
   - You can create custom exception rules that represent business/company/policy rules. You can also log details for debugging, then display a simple message back to the user. This would help with debugging and transparency.
#### Implementation Checkpoints
Checkpoint A
1. Pass
2. Pass
3. Pass
4. Pass
Checkpoint B
1. Pass
2. Pass
3. Pass
4. Pass
Checkpoint c
1. Pass
2. Pass
3. Pass
4. Pass
5. Pass
Checkpoint D
1. Pass
2. Pass
3. Pass
#### Manual Verification
1. Menu 1–7 appears; invalid `abc` → invalid menu message → menu returns.
   - Pass
2. Login `1001` / `1234` → `Login Successful` (balance starts at **$11000**).
   - Pass
3. Withdraw `20000` → **Insufficient Balance** / Transaction Canceled; still at menu.
   - Pass
4. Deposit `-100` → Amount must be greater than zero.
   - Pass
5. Deposit `abc` → Invalid numeric input messages; still at menu.
   - Pass
6. Deposit `1000` → Deposit Successful; balance becomes **12000**.
   - Pass
7. Mini Statement shows session rows and historical file lines (requires project-root cwd).
   - Pass
8. Login with wrong account `9999` → Account not found; still at menu.
   - Pass
9. Unchecked demo (menu 8 if added) prints three handled messages.
   - Pass
10. `logs/application.log` has ERROR entries; Exit `7` → `Thank You`.
    - Pass
#### Reflection Questions
1. What is the difference between checked and unchecked exceptions?
   - Checked exceptions must be caught or declared with throws while unchecked exceptions inherit from RuntimeException and do not have to be caught. Checked are usually for recoverable problems, while unchecked are usually caused by a programming error. 
2. Why should custom exceptions be used?
   - They make errors more specific and meaningful. They describe business rules which helps make the code easier to understand and maintain.
3. What is exception propagation?
   - It is when an exception moves up the stack call until it is finally caught, usually within main(). If a method cannot handle the exception, it passes it to the method that called it. This will continue until caught.
4. What is the purpose of `finally`?
   - It serves kind of like a safety net and will automatically run whether an exception occurs. It is mostly used with resources like files and database connections. The finally block automatically closes these which helps prevent resource leaks.
5. Why is `try-with-resources` preferred?
   - It automatically closes opened resources without the need to explicitly state it. It reduces boilerplate code and prevents resource leaks. It also makes code shorter and easier to read.
6. When should `throw` be used?
   - Use them when your code detects an error or invalid condition that should stop normal execution. It creates and throws an exception immediately so the caller has to decide how to handle it.
7. When should `throws` be used?
   - Use these in a method signature when the method may pass an exception to its caller instead of handling it. Usually used with checked exceptions. It informs callers that they must handle or declare the exception. 
8. Why is logging important in enterprise applications?
   - It helps with debugging and monitoring. It helps developers diagnose problems without exposing the user to technical details.
9. What happens if an exception is not handled?
   - If an exception is never caught, it continues propagating up the call stack. If it reaches the top without being handled, the program terminates and prints an error message and stack trace. This can cause the application to stop unexpectedly.
10. How does proper exception handling improve software reliability?
    - It prevents programs from crashing unexpectedly. It allows applications to recover from errors or provide clear messages to users. It also makes software easier to debug and maintain.
11. (Forward look) How would a future CRM map domain exceptions (not found / validation) to API errors using the same boundary-catch + log pattern—without claiming CRM is implemented today?
    - A future CRM could throw a domain exception if a customer tries to perform an action that is not allowed, such as updating a closed account or accessing a restricted feature. At the API boundary, the exception could be caught, logged for troubleshooting, and converted into an appropriate HTTP error response, such as 403 Forbidden. This would reuse the same boundary-catch and logging pattern while keeping business rules separate from API response handling.
#### Success Criteria
0. Pass
1. Pass
2. Pass
3. Pass
4. Pass
5. Pass
6. Pass