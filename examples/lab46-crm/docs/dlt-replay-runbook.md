# Lab 46 — DLT replay runbook

## When to replay

Poison messages on `crm.customer-events.v1.DLT` after root cause is fixed.

## Dry-run first

1. Inspect DLT records (headers: correlation `lab-request-001`, exception class).
2. Use the broker container, not the Windows host PATH:

```powershell
docker exec crm-kafka /opt/kafka/bin/kafka-console-consumer.sh `
  --bootstrap-server localhost:9092 `
  --topic crm.customer-events.v1.DLT --from-beginning `
  --property print.headers=true --max-messages 10 --timeout-ms 15000
```

3. Confirm the listener logs `CUS-1001` / `CUS-1002`, not email addresses.
4. Confirm idempotent handler will not double-apply side effects for `CUS-1001` / `CUS-1002`.

## Failure policy

### Not retryable → DLT

- `InvalidCustomerEventException`
- `UnsupportedEventVersionException`
- parse / deserialization failures

### Retryable with backoff → DLT if exhausted

- `DataAccessResourceFailureException`

### Lab budget

- Max elapsed: `10s`

## Limited replay

1. Rate-limit: 1 message/sec
2. Replay a small batch → verify CRM side effects once
3. Stop on unexpected errors or duplicate side effects; escalate
4. Keep the sample small enough to inspect headers and outcomes manually

## Evidence

Save the DLT inspection output or screenshot under `notes/screenshots/lab-46/` with no secrets/PII.
