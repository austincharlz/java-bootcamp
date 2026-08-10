# Lab27

Spring Boot transfer demo for Module 27 transaction management.

## Run

```bash
mvn -q -DskipTests package
mvn spring-boot:run
```

## Verify happy path (HTTP 200)

```bash
curl -s -H "X-Correlation-Id: lab-request-001" \
  -H "Content-Type: application/json" \
  -d '{"fromAccountId":"ACC-MAIN-1001","toAccountId":"ACC-LOYALTY-1001","amount":"50.00"}' \
  http://localhost:8080/api/transfers
```

Expected response: `{"status":"OK"}`.

## Verify rollback path (HTTP 500)

```bash
curl -s -i -H "X-Correlation-Id: lab-request-001" \
  -H "Content-Type: application/json" \
  -d '{"fromAccountId":"ACC-MAIN-1001","toAccountId":"ACC-FORCE-FAIL","amount":"10.00"}' \
  http://localhost:8080/api/transfers
```

Expected response: HTTP 500 from forced `IllegalStateException`, with MAIN balance unchanged.

## Tests

```bash
mvn -B test
```

Includes:
- `forceFailRollsBack`
- `happyPathMovesFunds`