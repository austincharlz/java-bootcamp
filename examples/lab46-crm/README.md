# Lab 46 — Kafka Resilience and Observability

Spring Kafka CRM lab with bounded retry, `.DLT` recovery, idempotent event handling, and basic observability notes.

## Key paths

| Path                                                             | Purpose                                  |
|------------------------------------------------------------------|------------------------------------------|
| `src/main/java/com/northstar/crm/config/KafkaErrorConfig.java`   | DLT recoverer + backoff + factory wiring |
| `src/main/java/com/northstar/crm/event/ProcessedEventStore.java` | In-memory idempotency store              |
| `docs/kafka-dashboard.md`                                        | Lag / DLT / retry / latency notes        |
| `docs/dlt-replay-runbook.md`                                     | Dry-run replay steps and failure policy  |

## Quick commands

```powershell
cd $env:USERPROFILE\java-bootcamp\examples\lab30-crm
docker compose up -d

cd $env:USERPROFILE\java-bootcamp\examples\lab46-crm
mvn -B test
```

```powershell
docker exec crm-kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
docker exec crm-kafka /opt/kafka/bin/kafka-console-consumer.sh `
  --bootstrap-server localhost:9092 `
  --topic crm.customer-events.v1.DLT --from-beginning `
  --property print.headers=true --max-messages 10 --timeout-ms 15000
curl.exe -fsS http://localhost:8080/actuator/prometheus
```

## Notes

- Main topic: `crm.customer-events.v1`
- DLT topic: `crm.customer-events.v1.DLT`
- Group: `crm-notifications`
- `ProcessedEventStore` is in-memory and resets on restart
- Do not log emails or use them as metric tags

## Deliverables

- Factory-wired `KafkaErrorConfig`
- DLT inspection evidence or tabletop note under `notes/screenshots/lab-46/`
- Completed dashboard and replay docs
