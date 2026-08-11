# Lab 30 — Topic and Key Map

## Reference

| Concept | Northstar choice |
| --- | --- |
| Main topic | crm.customer-events.v1 |
| DLQ topic | crm.customer-events.v1.dlq |
| Partitions (lab) | 3 |
| Record key | customerId (e.g. CUS-1001) |

## Step 2 — Keying reason
Records within a partition are stored in order; ordering is not guaranteed across partitions, however.

## Step 3 — Versioning
It indicates the current version, with future changes indicated by changing v1 to v2 and so on.

## Step 4 — DLQ trigger
1. Invalid event data (e.g. missing required fields, invalid JSON format, etc.)
2. Repeated processing failures (e.g. consumer application fails to process the record multiple times)

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.