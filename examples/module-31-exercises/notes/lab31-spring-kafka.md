# Lab 31 — Spring Kafka Roles

## Reference

| Kafka idea | Spring Boot piece |
| --- | --- |
| Produce record | KafkaTemplate.send(...) |
| Consume record | @KafkaListener |
| Bootstrap servers | spring.kafka.bootstrap-servers |
| Group id | spring.kafka.consumer.group-id |

## Step 1 — Study table

Copy the reference table into `notes/lab31-spring-kafka.md`.

## Step 2 — CRM story

After HTTP creates Amina, service calls `KafkaTemplate` to `crm.customer-events.v1` with key `CUS-1001`.

## Step 3 — Listener story

Notifications listener uses group `crm-notifications` and processes the JSON envelope.

## Step 4 — Gap check
Are they immutable.

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.