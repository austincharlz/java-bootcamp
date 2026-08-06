# Lab 23 CRM

Spring Boot CRM slice for Lab 23 with a customer API and Actuator smoke checks.

## Runbook

1. Start the app:
   `mvn spring-boot:run`
2. Check Actuator:
   `curl http://localhost:8080/actuator/health`
   `curl http://localhost:8080/actuator/info`
3. Create customers:
   `curl -H "X-Correlation-Id: lab-request-001" -H "Content-Type: application/json" -d "{\"id\":\"CUS-1001\",\"name\":\"Amina Khan\",\"email\":\"amina.khan@example.com\",\"status\":\"ACTIVE\"}" http://localhost:8080/api/customers`
   `curl -H "X-Correlation-Id: lab-request-001" -H "Content-Type: application/json" -d "{\"id\":\"CUS-1002\",\"name\":\"Ravi Singh\",\"email\":\"ravi.singh@example.com\",\"status\":\"PROSPECT\"}" http://localhost:8080/api/customers`
4. Read customers:
   `curl http://localhost:8080/api/customers/CUS-1001`
   `curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8080/api/customers/CUS-MISSING`
5. Run smoke tests twice:
   `mvn -B test`
   `mvn -B test`

## Profiles

- `application-dev.yml` raises CRM logging and always shows health details for local lab work.
- `application-prod.yml` keeps logging conservative and only exposes health.

## Notes

- Actuator exposure here is lab-only and should be tightened for production.
- Evidence and failure experiment excerpts are stored under `C:\Users\austi\java-bootcamp\notes\screenshots\lab-23`.