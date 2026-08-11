# Lab 30 — Event Envelope Sketch

## Step 1 — Headers

Envelope fields: `eventType`, `eventVersion`, `occurredAt`, `correlationId`, `customerId`, `payload`.

## Step 2 — Amina sample
Sketch `CustomerCreated` for `CUS-1001` Amina Khan with `correlationId=lab-request-001`.
```bash
CustomerCreated
  eventType: CustomerCreated
  eventVersion: 1
  occurredAt: <timestamp>
  correlationId: lab-request-001
  customerId: CUS-1001
  payload:
    fullName: Amina Khan
    email: amina@example.com
    status: ACTIVE
```

## Step 3 — Ravi sample

Sketch `CustomerStatusChanged` for `CUS-1002` Ravi Singh (`ACTIVE` → `SUSPENDED` or similar).
```bash
CustomerStatusChanged
  eventType: CustomerStatusChanged
  eventVersion: 1
  occurredAt: <timestamp>
  correlationId: lab-request-002
  customerId: CUS-1002
  payload:
    fullName: Ravi Singh
    email: ravi@example.com
    status: SUSPENDED
```

## Step 4 — Compatibility note
Consumers must ignore unknown payload fields (forward compatible).

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.