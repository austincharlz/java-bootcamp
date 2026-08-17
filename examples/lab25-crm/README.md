# Northstar CRM SOAP + REST

Spring Boot CRM slice for Lab 25 with the original REST API plus a contract-first Spring-WS SOAP endpoint that shares
the same `CustomerService`.

## Runbook

1. Start the app:
   `mvn spring-boot:run`
2. Check the SOAP contract and timed-path request:
   `curl http://localhost:8080/ws/customers.wsdl`
   `curl -H "Content-Type: text/xml; charset=utf-8" --data @requests/get-customer.xml http://localhost:8080/ws`
3. Check the seeded REST reads from Step 5:
   `curl -H "X-Correlation-Id: lab-request-001" http://localhost:8080/api/customers/CUS-1001`
   `curl -H "X-Correlation-Id: lab-request-001" http://localhost:8080/api/customers/CUS-1002`
4. Create the Step 6 sample customer:
   `Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/customers" -Headers @{ "X-Correlation-Id" = "lab-request-001" } -ContentType "application/json" -Body '{"id":"CUS-1003","name":"Maya Chen","email":"maya@example.com","status":"PROSPECT"}'`
   Duplicate creates like `CUS-1001` are rejected by `CustomerService` and currently surface as an error response. If
   you specifically want `curl.exe` in PowerShell, send the JSON from a file with `--data-binary @payload.json`; inline
   single-quoted JSON can produce a 400 parse error on Windows PowerShell.
5. Check Actuator:
   `curl http://localhost:8080/actuator/health`
   `curl http://localhost:8080/actuator/info`
6. Run the focused Step 7 service tests:
   `mvn -q test -Dtest=CustomerServiceTest`
7. Run the full lab test suite:
   `mvn -B test`

## Layering Notes

- `CustomerController` is a thin HTTP adapter and only calls `CustomerService`.
- `CustomerService` owns duplicate and not-found rules and depends on the `CustomerRepository` interface instead of an
  internal map.
- `InMemoryCustomerRepository` seeds `CUS-1001` and `CUS-1002`, while list verification stays in tests rather than a
  `GET /api/customers` endpoint.
