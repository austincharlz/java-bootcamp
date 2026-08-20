# Lab 40 — Fill SAST Path TODOs

## Step 1 — Copy template

Endpoint: GET /api/customers/{id} Authz check: role check + object-level customer ownership/tenant scope TODOs before
returning customer data Sink (SQL/file/log): _____ Customer fixture used: CUS-1001 Risk if missing check: _____

## Step 2 — Fill for customer read

- Endpoint: GET /api/customers/{id}
- Authz check: role check + object-level customer ownership/tenant scope TODOs before returning customer data
- Sink (SQL/file/log): _____
- Customer fixture used: CUS-1001
- Risk if missing check: _____

## Step 3 — Second path

- Endpoint: PUT /api/customers/{id}/status
- Authz check: role check + object-level customer ownership/tenant scope TODOs before changing customer state
- Sink (SQL/file/log): _____
- Customer fixture used: CUS-1002
- Risk if missing check: _____

## Step 4 — Self-check

- No passwords, tokens, or real PII appear in this note.
- Items still marked `_____` are the ones Lab 40 will prove with code.
- This is a pre-lab note only; it is not the full graded lab.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.