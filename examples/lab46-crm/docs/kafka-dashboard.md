# Lab 46 — Kafka dashboard notes

## Event flow map

| Producer                 | Topic                    | Key          | Group               | Side effect                                                | Owner    | Redaction                        |
|--------------------------|--------------------------|--------------|---------------------|------------------------------------------------------------|----------|----------------------------------|
| `CustomerEventPublisher` | `crm.customer-events.v1` | `customerId` | `crm-notifications` | Publish customer lifecycle events for downstream consumers | CRM team | Never tag or log email addresses |

## Signals

| Signal             | Why it matters                 | Alert sketch                                                       |
|--------------------|--------------------------------|--------------------------------------------------------------------|
| Consumer lag       | Partition stuck / slow handler | Warn if growing for 5m; critical if >500 messages                  |
| DLT message rate   | Poison / contract break        | Warn if >0 for 5m; critical if sustained >5/min                    |
| Retry count        | Transient vs permanent         | Warn if retries spike above baseline; critical if it never settles |
| Processing latency | SLA risk                       | Warn if p95 > 2s; critical if p95 > 5s                             |

## False confidence

Lag = 0 while DLT is growing still means customer events are failing. Track both lag and DLT rate together in ops notes.

## Fixtures

Synthetic only: `CUS-1001`, `CUS-1002`, correlation `lab-request-001`. Use `topic` / `outcome` labels and redact emails
from metric tags.
