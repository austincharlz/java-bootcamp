# Lab 31 — Fill Spring Kafka TODOs

## Step 1 — Paste snippet

Create `notes/lab31-todos.md`:

```bash
spring.kafka.bootstrap-servers: localhost:9092
spring.kafka.consumer.group-id: crm-notifications

@Service
class CustomerEventPublisher {
  private final KafkaTemplate<String, String> template;
  void publishCreated(String customerId, String json) {
    template.send("crm.customer-events.v1", customerId, json);
  }
}

@KafkaListener(topics = "crm.customer-events.v1", groupId = "crm-notifications")
void onEvent(String payload) { }
```

## Step 3 - Key Reminder
Key argument must be CUS-1001 / CUS-1002, not a random UUID.

## Step 4 - DLT Blank
// TODO Lab 31: route poison messages to crm.customer-events.v1.dlq