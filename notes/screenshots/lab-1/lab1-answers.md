Questions/Notes:
* Difference between HelloWorld.java and HelloWorld.class
    * The .java file contains the code the developer wrote while the .class file has the bytecode that the JVM will run. 
* Step 6 Table: 
   * Already filled out
* JVM Memory Options
   * size_t InitialHeapSize = 264241152
   * size_t MaxHeapSize = 4198498304
   * bool UseG1GC = true
Implementation Checkpoints:
* Checkpoint A
  * It did not need .java file after because it is executing the .class file which has the bytecode that it needs. 
* Checkpoint B
  * Main frame pushes a new stack frame into the stack where it does the calculation and then returns the result. It is then immediately popped off after the return. 
* Checkpoint C
Evidence Capture:
1. Pass
2. Pass
3. Pass
4. Pass
5. Pass
6. Pass
7. Pass
Security and Production Review:
1. They might forbid it because there could be differences between machines that are not regarded in the .class file. Different machines might need different configurations. 
2. Only authorized individuals should have access to these dumps because it contains sensitive data. This could cause a security breach. 
3. You should never print passwords/keys because it can be exposes in places like GitHub (if using that), in version logs, etc. 
4. If a class file appears before build output, they could be trying hijack the program by running their own code. 
5. It is dangerous because you can run into memory and performance issues down the line. 
6. Use Maven to make sure compilation standard across machines, make sure JVM writes heap dumps in a secure location only authorized users, and make sure to never print passwords/keys anywhere.