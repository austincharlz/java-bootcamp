#### Implementation Checkpoints

Checkpoint A

- Pass
- Pass
- Pass Checkpoint B
- Pass
- Pass
- Pass Checkpoint C
- Pass
- Pass
- Pass Checkpoint D
- Pass
- Pass
- Pass

#### Reflection Questions

1. Returning available=false instead of failing hard. A 500 breaks the page; a fake success is worse because agents
   would act on invented account data.
2. The test captures the serve-event count after the circuit opens, fires 3 more probes, then asserts the count is
   unchanged. Combined with the circuitBreaker.getState () == OPEN assertion, this proves fail-fast with zero additional
   HTTP requests.
3. CircuitBreaker.decorateSupplier around supplyAsync always saw the supplier return a future (never throw), so every
   failed async call was silently recorded as a CB success and the circuit never opened. Fix: decorate the synchronous
   client.fetch with decorateCallable first, then lift into a future.