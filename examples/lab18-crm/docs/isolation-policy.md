#### Isolation Policy
Which tests use real in-memory repo vs mocks
- Lab 17 style (in-memory repo): integration-style tests that exercise DefaultCustomerService together with a simple collection-backed repository to validate wiring and state changes.
- Lab 18 style (mocks): true unit tests that mock CustomerRepository with Mockito to isolate service/validator logic.

How to choose stub (when/given) vs verify (verify/then().should)
- Stub (when/given): use to provide collaborator return values needed to drive the code path (inputs/fixtures).
- Verify (verify/then().should): use to assert interactions/side-effects (save called, or never called).
- Rule: stub for inputs, verify for outputs; keep stubs minimal.

Correlation ID expectations on exception paths
- Domain errors (BusinessException) started inside service should carry the provided correlationId; tests should assert exception type and that the correlationId is preserved when applicable.
- Validation errors thrown before service-side correlation logic (IllegalStateException/IllegalArgumentException) may not include a correlationId—assert only the expected exception and message.

Why both styles can coexist
- Unit (mock) tests are fast and focused; in-memory integration-style tests catch wiring/regression issues. Use both for complementary confidence: units for edge cases, in-memory for end-to-end behavior.
