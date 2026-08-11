# Lab 31 — Listener Sketch

## Step 1 — Method outline

in this notes file.: `@KafkaListener(topics="crm.customer-events.v1", groupId="crm-notifications")` void onCustomerEvent(...).

## Step 2 — Second group
```bash
@KafkaListener(
    topics = "crm.customer-events.v1",
    groupId = "crm-audit"
)
void onAuditEvent(String message) {
    // record customer event for auditing
}
```

## Step 3 — Payload type

Pick String initially — it keeps the pre-lab listener simple and lets us inspect the raw JSON before committing to a CustomerEvent DTO.
## Step 4 — Correlation

Log the correlationId from the event envelope (for example, lab-request-001) when processing the event so support can trace the event back to the original request.
## Scope
Pre-lab only — do not finish the full graded lab in this exercise.