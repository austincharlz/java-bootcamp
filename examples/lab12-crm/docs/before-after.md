#### Step 3 - CustomerServiceTest 
- Fails tests until Step 4 and 5 are finished. 
#### Step 4 - Refactor and Method Boundaries
- Replaced messy API with refactored version.
- Created helpers as stated and used 
#### Step 7 - Run Tests and Capture Evidence
1. [Smells Table](smells.md)
2. Methods Before
   - doStuff(), get()
3. Methods After
   - create(), getbyId(), createCustomer(), getCustomer(), updateStatus(),
     requireNonblank(), requireUniqueId(), requireExisting()
4. Test Output Excerpt
```bash
PS C:\Users\austi\java-bootcamp\examples\lab12-crm> mvn clean test
[INFO] Scanning for projects...
...
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.northstar.crm.entity.CustomerTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.060 s -- in com.northstar.crm.entity.CustomerTest
[INFO] Running com.northstar.crm.service.CustomerNotifierMockTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.010 s -- in com.northstar.crm.service.CustomerNotifierMockTest
[INFO] Running com.northstar.crm.service.CustomerServiceTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.005 s -- in com.northstar.crm.service.CustomerServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.238 s
[INFO] Finished at: 2026-07-30T11:10:59-04:00
[INFO] ------------------------------------------------------------------------
```
5. Manual Demo Transcript
```bash
PS C:\Users\austi\java-bootcamp\examples\lab12-crm> mvn clean compile exec:java "-Dexec.mainClass=com.northstar.crm.Main"
[INFO] Scanning for projects...
...
=== Creating customers ===
Created: CUS-1001 | Amina Khan | Status: ACTIVE
Created: CUS-1002 | Ravi Singh | Status: PROSPECT

=== Retrieving customers ===
Retrieved: CUS-1001 | Amina Khan
Retrieved: CUS-1002 | Status: PROSPECT

=== Updating customer status ===
Updated: CUS-1002 | Status: ACTIVE

=== Testing error handling ===
Duplicate rejected: Customer ID 'CUS-1001' already exists
Unknown ID rejected: Customer ID 'CUS-9999' not found [correlation: 96bd32d6-211d-419e-ac25-f1361e97898f]

? All demos completed successfully!
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.389 s
[INFO] Finished at: 2026-07-30T11:47:47-04:00
[INFO] ------------------------------------------------------------------------
```