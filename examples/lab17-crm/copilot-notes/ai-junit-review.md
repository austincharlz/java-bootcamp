#### lab17-001
1. Assertations are explicit so regresions will fail.
2. There is a shared fixture (CUS-1001/CUS-1002).
3. There are no phantom Spring/JPA imports.
4. There is a `@BeforeEach` method that sets up the test data.
5. `mvn -q test` runs the tests and pass.