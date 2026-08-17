# Northstar CRM SOAP + REST

Spring Boot CRM slice for Lab 24 with the original REST API plus a contract-first Spring-WS SOAP endpoint that shares
the same `CustomerService`.

## Runbook

1. Start the app:
   `mvn spring-boot:run`
2. Check the SOAP contract and timed-path request:
   `curl http://localhost:8080/ws/customers.wsdl`
   `curl -H "Content-Type: text/xml; charset=utf-8" --data @requests/get-customer.xml http://localhost:8080/ws`
3. Check Actuator:
   `curl http://localhost:8080/actuator/health`
   `curl http://localhost:8080/actuator/info`
4. Create customers:
   `curl -H "X-Correlation-Id: lab-request-001" -H "Content-Type: application/json" -d "{\"id\":\"CUS-1001\",\"name\":\"Amina Khan\",\"email\":\"amina.khan@example.com\",\"status\":\"ACTIVE\"}" http://localhost:8080/api/customers`
   `curl -H "X-Correlation-Id: lab-request-001" -H "Content-Type: application/json" -d "{\"id\":\"CUS-1002\",\"name\":\"Ravi Singh\",\"email\":\"ravi.singh@example.com\",\"status\":\"PROSPECT\"}" http://localhost:8080/api/customers`
5. Read customers:
   `curl http://localhost:8080/api/customers/CUS-1001`
   `curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8080/api/customers/CUS-MISSING`
6. Run smoke tests:
   `mvn -B test`

## Profiles

- `application-dev.yml` raises CRM logging and always shows health details for local lab work.
- `application-prod.yml` keeps logging conservative and only exposes health.

## Notes

- Timed-path SOAP stays unsecured; UsernameToken is full-path homework only.
- Actuator exposure here is lab-only and should be tightened for production.