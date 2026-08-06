# Lab 25 — Service Test Plan

| Case | Setup | Expect |
| --- | --- | --- |
| get CUS-1001 | seeded repo | ACTIVE Amina |
| duplicate create | existing id | conflict/exception |
| get CUS-9999 | empty/missing | not-found |
| create CUS-new | fresh | saved |

No Spring Boot required for pure unit tests.

## Scope
Pre-lab only.