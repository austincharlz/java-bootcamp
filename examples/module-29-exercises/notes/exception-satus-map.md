# Lab 29 — Exception to Status Map

| Case | Status | Code |
| --- | --- | --- |
| Invalid email/body | 400 | VALIDATION_FAILED |
| CUS-9999 | 404 | CUSTOMER_NOT_FOUND |
| Duplicate CUS-1001 | 409 | DUPLICATE_CUSTOMER |
| Illegal status transition | 400/422 | ILLEGAL_TRANSITION |

## Scope
Pre-lab only.