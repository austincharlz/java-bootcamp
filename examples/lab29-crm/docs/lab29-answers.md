#### Implementation Checkpoints
Checkpoint A
- Pass
- Pass
- Pass
  Checkpoint B
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
1. The design decision that most affected correctness was running validation at the controller boundary (`CustomerRequest` constraints + `@Valid`), so invalid input is rejected before service logic.
2. Stability is proven by `ErrorEnvelopeTest` asserting both status codes and JSON body fields for 400/404/409 plus 401 security behavior, with all 4 tests passing.
3. Missing Bearer auth was the hardest to diagnose because 401 occurs before controller validation/exception handling, which can look like validation or mapping is broken.