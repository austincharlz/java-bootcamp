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
1. The biggest correctness decision was publish-after-success because it guarantees we never emit events for writes that failed, but it still leaves a dual-write gap if DB commit succeeds and Kafka publish fails; an outbox is stronger for production guarantees.
2. Once-only side effects are proven by the integration behavior where re-sending the same eventId results in duplicate_event_ignored and the duplicate test shows only one handled occurrence for that eventId.
3. The hardest failure was DLT wiring, because it depends on correct error classification, backoff behavior, and destination naming (.dlq) all being aligned, while deserialization and await timing were more direct to diagnose.