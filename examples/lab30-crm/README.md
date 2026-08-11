# Lab 30 CRM — Brief Summary

This lab validates a Kafka event foundation for CRM: KRaft broker on `localhost:9092`, explicit primary + DLQ topics, keyed customer events, Java producer safety (`acks=all`, idempotence), and consumer-group behavior (competing vs independent) with lag/replay evidence.

## Commands

```powershell
Set-Location C:\Users\austi\java-bootcamp\examples\lab30-crm

# Start broker
docker compose up -d

# Create topics
docker exec crm-kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --if-not-exists --topic crm.customer-events.v1 --partitions 3 --replication-factor 1
docker exec crm-kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --if-not-exists --topic crm.customer-events.v1.dlq --partitions 1 --replication-factor 1

# Run Java producer (prints topic/key/partition/offset)
mvn -q test
mvn -q -DskipTests package
mvn -q -DskipTests exec:java "-Dexec.mainClass=com.northstar.crm.event.CustomerEventProducer"

# Inspect group lag
docker exec crm-kafka /opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group crm-notifications

# Stop broker
docker compose down
```

## Reflection Questions

1. Keying records by `customerId` was the most important decision, because it preserved per-customer event order on a single partition and made lifecycle transitions reliable.

2. Producer metadata output (`topic`, `partition`, `offset`) confirms successful publish, and the consumer evidence files under `notes\screenshots\lab-30` show keyed records being read by both `crm-notifications` and `crm-audit` groups.

3. Lag behavior was the hardest to diagnose at first, because the group can look healthy until one consumer is stopped long enough to create observable lag; describing the group before and after catch-up made it clear.