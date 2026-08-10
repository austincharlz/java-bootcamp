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
1. The stateless token design most affected correctness because every request had to be authenticated from the bearer token, eliminating hidden server-side session state and making security behavior consistent per request.
2. Role separation is proven by both runtime behavior and tests: agent1 can access /api/customers/ but gets 403 on /api/admin/ping, while admin1 gets 200 on both; SecurityPathTest codifies this with passing agentCanReadCustomerButNotAdmin and adminCanPing.
3. The hardest failure was distinguishing 401 vs 403 when filter-chain behavior was incomplete, because missing/invalid authentication, access denial, and filter ordering can all look similar until the entry point/denied handlers and matcher paths are configured correctly.
