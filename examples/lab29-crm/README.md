# Lab 29 CRM — Validation and Exception Handling

This project implements the Lab 29 timed-path requirements: Bean Validation on customer requests, `@Valid` controller enforcement, and a global `ErrorResponse` contract for 400/404/409/500 while keeping Lab 28 Bearer security in place.

## Deliverables Completed

| # | Deliverable | Status |
| - | ----------- | ------ |
| 1 | Bean Validation + `GlobalExceptionHandler` + existing `ErrorResponse` | Pass |
| 2 | `ErrorEnvelopeTest` with validation/not-found/duplicate/security cases (**Tests run: 4**) | Pass |
| 3 | Successful-path evidence with Bearer (`CUS-1001`, `CUS-1002`) | Pass |
| 4 | Controlled-failure evidence with Bearer (400/404/409 envelopes) | Pass |
| 5 | Notes in `docs/error-contract.md` | Pass |
| 6 | Run and cleanup instructions | Pass |
| 7 | No secrets or generated build directories committed | Pass |

## Exception Map

```text
MethodArgumentNotValidException -> 400 Bad Request
IllegalArgumentException        -> 404 Not Found
IllegalStateException           -> 409 Conflict
Exception                       -> 500 Internal Server Error ("Unexpected error")
```

## Run

```bash
cd ~/java-bootcamp/examples/lab29-crm
mvn -q spring-boot:run
```

## Test

```bash
cd ~/java-bootcamp/examples/lab29-crm
mvn -B test
```

Expected: `Tests run: 4` and `BUILD SUCCESS`.

## Cleanup

```bash
cd ~/java-bootcamp/examples/lab29-crm
# stop spring-boot:run (Ctrl+C)
mvn -q clean
git status
```

## Bearer Auth + API Evidence Commands

```bash
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"agent1","password":"agent1"}' | jq -r .accessToken)

# Validation failure (400)
curl -s -i -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -H "X-Correlation-Id: lab-request-001" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"id":"","name":"","email":"not-an-email","status":"ACTIVE"}'

# Missing customer (404)
curl -s -i http://localhost:8080/api/customers/CUS-9999 \
  -H "X-Correlation-Id: lab-request-001" \
  -H "Authorization: Bearer $TOKEN"

# Duplicate customer (409)
curl -s -i -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -H "X-Correlation-Id: lab-request-001" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"id":"CUS-1001","name":"Amina Khan","email":"amina@northstar.test","status":"ACTIVE"}'
```

## Notes

- Customer APIs require Lab 28 Bearer authentication.
- `ErrorResponse` shape is preserved (`timestamp`, `status`, `error`, `message`, `correlationId`, `violations[{field,message}]`).
- Do not include passwords or JWT values in saved evidence.