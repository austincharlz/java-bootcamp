#### Implementation Checkpoint
- Checkpoint A
  - Pass
  - Pass
  - Pass
- Checkpoint B
    - Pass
    - Pass
    - Pass
- Checkpoint C
    - Pass
    - Pass
    - Pass
- Checkpoint D
    - Pass
    - Pass
    - Pass
#### Reflection Questions
1. The @BeforeEach isolation pattern—creating a fresh InMemoryCustomerRepository, CustomerValidator, and DefaultCustomerService for every test—prevented state leakage between tests and ensured each test ran independently with predictable behavior.
2. mvn -q test exited with code 0 (all tests passed), parameterized tests confirmed legal and illegal transitions behave correctly, and the JaCoCo report showed 87% instruction coverage in com.northstar.crm.service, exceeding the 0.80 minimum.
3. The duplicateIdThrowsConflict test initially expected BusinessException but the validator throws IllegalStateException—the type mismatch took a round-trip to the validator code to resolve, because the error message didn't immediately clarify which exception type was actually thrown.