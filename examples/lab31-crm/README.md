# Lab 31 CRM (Spring Kafka)

Small CRM lab that publishes keyed `CustomerEvent` messages after successful customer writes, consumes them with a typed
`@KafkaListener`, applies idempotency by `eventId`, and routes poison messages to a `.dlq` topic after bounded retry.

## Quick commands

```bash
# From this directory
mvn test
mvn spring-boot:run
```

```bash
# Optional: start Lab 30 Kafka broker for manual API/event demos
docker compose -f ../lab30-crm/compose.yaml up -d
```