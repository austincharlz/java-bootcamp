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
1. Constructor injection mattered most because it made the dependencies explicit and prevented hidden wiring issues.
2. The unit test passed, the app started successfully, and create/get requests through /api/customers returned the expected customer payload.
3. The initial mismatches between the lab’s expected Customer shape and the code’s old accessor names (getCustomerId/getFullName) were the hardest to spot because they caused compile errors in several places.