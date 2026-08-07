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
1. Putting duplicate and not-found rules in `CustomerService` mattered most because both REST and SOAP now enforce the same behavior through one layer instead of each adapter inventing its own checks.
2. The controller only delegates to `CustomerService`, `CustomerService` only talks to `CustomerRepository`, seeded GETs resolve through the service, and the focused `CustomerServiceTest` passes without starting the full MVC stack.
3. The most confusing failure was duplicate create behavior because old runbook examples posted seeded IDs, which made curl look broken even though the service was correctly rejecting duplicates.
