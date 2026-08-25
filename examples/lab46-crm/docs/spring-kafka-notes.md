# Lab 31 — Spring Kafka notes

## Publish path

`CustomerService` publishes a `CustomerEvent` only after successful write operations:

- `create(...)` emits `CustomerCreated`
- `updateStatus(...)` emits `CustomerStatusChanged`

Events are keyed by `customerId` (`CUS-1001`, `CUS-1002`) and include `correlationId` (`lab-request-001` default).

## Idempotency

`ProcessedEventStore` uses `ConcurrentHashMap.newKeySet()` and `markIfNew(eventId)`:

- first delivery: returns `true` and listener handles the event
- replay/duplicate: returns `false`, listener logs `duplicate_event_ignored`, no duplicate side effect

## DLT

This lab uses **Lab 30-style `.dlq` naming** through `DeadLetterPublishingRecoverer`:

- source topic: `crm.customer-events.v1`
- dead-letter topic: `crm.customer-events.v1.dlq`

`DefaultErrorHandler` retries with bounded backoff (`1s`, `2` retries), then publishes to DLT. Non-retryable contract
failures:

- `InvalidCustomerEventException`
- `UnsupportedEventVersionException`

DLT records include headers identifying original topic, partition, offset, and exception details.

## Runbook

```bash
# Start Kafka broker (Lab 30)
docker compose -f ../lab30-crm/compose.yaml up -d

cd ~/java-bootcamp/examples/lab31-crm
mvn -q test
mvn -q spring-boot:run
# Create CUS-1001 / update CUS-1002 via API
# Observe: customer_event_published / customer_event_received / duplicate_event_ignored
```

## Publish timing note (DB vs Kafka)

- **Publish-after-success (this lab):** simple and direct; if DB commit succeeds but Kafka publish fails, downstream
  consumers miss the event.
- **Transactional outbox (production):** persist an outbox row in the same DB transaction, then relay to Kafka for
  stronger delivery guarantees.