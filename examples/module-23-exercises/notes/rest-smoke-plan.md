# Lab 23 — REST Smoke Plan

1. Start: mvn spring-boot:run
2. GET /actuator/health → UP
3. POST /api/customers for CUS-1001 (Amina, ACTIVE) with correlation lab-request-001
4. GET /api/customers/CUS-1001
5. Repeat create/get for CUS-1002 (Ravi, PROSPECT)
6. Capture screenshots under notes/screenshots/lab-23/

## Scope
Pre-lab only.