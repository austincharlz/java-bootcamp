# Lab 24 — SOAP Fault Versus REST Error

| Case | SOAP | REST |
| --- | --- | --- |
| Missing CUS-9999 | SOAP Fault (Client/business) | 404 JSON problem details |
| Validation fail | SOAP Fault | 400 JSON |
| Auth missing | WS-Security fault | 401/403 (later Lab 28) |

## One rule
Same CustomerService exception; different protocol adapters.

## Scope
Pre-lab only.