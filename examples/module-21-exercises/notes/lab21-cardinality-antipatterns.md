# Lab 21 — Cardinality Anti-Patterns

| Label                         | OK?          |
|-------------------------------|--------------|
| outcome=success               | failure      | Yes |
| customerId=CUS-1001           | No           |
| correlationId=lab-request-001 | No, use logs |

## Where ids go
IDs go in logs/traces.

## Good metric sketch
crm.customer.create with outcome tag.

## Scope
Pre-lab only.