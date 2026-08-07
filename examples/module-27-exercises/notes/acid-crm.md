# Lab 27 — ACID for CRM Transfers

| Letter | CRM observation |
| --- | --- |
| A | Forced fail leaves MAIN unchanged; no success log |
| C | After happy path, balances and log agree |
| I | Default isolation; no dirty mid-transfer reads required for Pass |
| D | Committed happy path survives restart (note H2 mode) |

## Scope
Pre-lab only.