# Lab 30 — Kafka runbook and hand-off notes

## Frozen lab values

| Item | Lab value |
| ---- | --------- |
| Bootstrap (host) | `localhost:9092` |
| Primary topic | `crm.customer-events.v1` (3 partitions) |
| DLQ topic | `crm.customer-events.v1.dlq` (1 partition) |
| Record key | `customerId` (`CUS-1001`, `CUS-1002`) |
| Sample correlation | `lab-request-001` |
| Demo groups | `crm-notifications` (competing), `crm-audit` (independent) |

## Produce → broker → consume

The producer publishes JSON envelopes to `crm.customer-events.v1` with key=`customerId`. Kafka persists each record to the partition selected by the key hash. Consumers in `crm-notifications` and `crm-audit` then read the same stream independently using their own group offsets.

## Keying and ordering

Keying by `customerId` keeps events for one customer on one partition, preserving per-customer order (for example, create then status change for `CUS-1001`). There is no global ordering across customers because different keys can map to different partitions and be consumed in parallel.

## Delivery semantics and replay

This lab demonstrates at-least-once delivery: replays can redeliver previously seen records, so Lab 31 consumers must be idempotent by `eventId`. `--from-beginning` is a learning/recovery tool; production replay needs an explicit policy and safe dedupe behavior.

## DLQ purpose

`crm.customer-events.v1.dlq` is reserved for poison or repeatedly failing records so normal consumption of `crm.customer-events.v1` can continue. In production this topic must be secured and monitored like the primary stream.

## Commands a peer should run

```bash
docker compose up -d
docker exec crm-kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --if-not-exists --topic crm.customer-events.v1 --partitions 3 --replication-factor 1
docker exec crm-kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --if-not-exists --topic crm.customer-events.v1.dlq --partitions 1 --replication-factor 1
docker exec crm-kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic crm.customer-events.v1
docker exec crm-kafka /opt/kafka/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic crm.customer-events.v1 --property parse.key=true --property key.separator=:
docker exec crm-kafka /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic crm.customer-events.v1 --from-beginning --property print.key=true --property print.partition=true --property print.offset=true --property print.timestamp=true
docker exec crm-kafka /opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group crm-notifications
```

## Production checklist notes

`PLAINTEXT`, replication factor `1`, and auto-create behavior are lab-only defaults. Production requires TLS/SASL, higher replication, ACLs, and controlled topic creation.

## Evidence files captured

Evidence excerpts are under `notes/screenshots/lab-30/`, including:

- `01-compose-ps.txt`, `02-topic-describe-events.txt`, `03-topic-describe-dlq.txt`
- `14-step7-notifications-a.txt`, `15-step7-notifications-b.txt`, `16-step7-audit.txt`
- `06-lag-before-catchup.txt`, `07-notifications-catchup-consume.txt`, `08-lag-after-catchup.txt`
- `09-failure-kafka-stopped-command.txt`, `10-replay-a.txt`, `11-replay-b.txt`, `12-failure-typo-topic-list.txt`