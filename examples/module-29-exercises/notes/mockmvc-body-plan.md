# Lab 29 — MockMvc Body Assertions Plan

| Case | Status | Body asserts |
| --- | --- | --- |
| Bad email | 400 | code=VALIDATION_FAILED; violations not empty; correlationId |
| CUS-9999 | 404 | code=CUSTOMER_NOT_FOUND |
| Duplicate | 409 | code=DUPLICATE_CUSTOMER |
| GET CUS-1001 | 200 | happy path (not error envelope) |

## Scope
Pre-lab only.