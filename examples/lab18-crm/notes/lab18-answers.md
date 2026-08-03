#### Implementation Checkpoint
Checkpoint A
- Pass
- Pass
- Pass
- Pass
Checkpoint B
- Pass
- Pass
- Pass
- Pass
Checkpoint C
- Pass
- Pass
- Pass
Checkpoint D
- Pass
- Pass
- Pass
#### Reflection Questions
1. Sharing the same mock CustomerRepository between DefaultCustomerService and CustomerValidator. That ensures validator checks and service saves hit the same mocked state; using @InjectMocks alone (or separate mocks) can hide interaction mismatches and let invalid flows pass.
2. Concrete interaction assertions: verify(repository).existsById(...) and verify(repository).existsByEmail(...); verify(repository, never()).save(...); and ArgumentCaptor captures (or argThat) showing the saved Customer has expected fields/status when save is called. Also assertThrows for expected exceptions and final returned state when successful.
3. UnnecessaryStubbingException. It’s non-obvious because tests still exercise behavior but Mockito’s strictness flags unused stubs; it often points to test setup drift (leftover stubs) rather than production code, so root-cause requires checking test fixture changes and Mockito strictness rather than the service logic.