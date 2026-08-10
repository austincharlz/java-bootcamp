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
1. The most important design decision was making TransferService.transfer(...) the single @Transactional boundary so debit, credit, and log write are one unit of work. Keeping the controller thin prevented partial commits from web-layer handling.
2. Rollback is proven by the forced failure path to ACC-FORCE-FAIL: the call throws, MAIN balance remains unchanged from its pre-call value, and no new TransactionLog row is persisted for that failed transfer. The forceFailRollsBack test asserts both conditions.
3. The hardest failure mode was proxy/self-invocation behavior, because a transactional method can silently lose rollback semantics if called through this instead of the Spring proxy. Exception type was more straightforward once we consistently used unchecked exceptions and did not swallow them.