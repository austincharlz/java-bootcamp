# Lab 28 CRM Security Runbook

This lab secures the CRM API with stateless bearer auth, role-based route protection, and regression tests.

## Demo users

| Username | Password | Role |
| --- | --- | --- |
| `agent1` | `agent1` | `AGENT` |
| `admin1` | `admin1` | `ADMIN` |

## Route authorization matrix

| Path | Access rule |
| --- | --- |
| `/api/auth/login` | `permitAll` |
| `/actuator/health` | `permitAll` |
| `/error` | `permitAll` |
| `/api/customers/**` | `hasAnyRole("AGENT","ADMIN")` |
| `/api/admin/**` | `hasRole("ADMIN")` |
| any other route | authenticated |

## Run and verify

```bash
export JWT_SECRET='lab-only-change-me'
mvn -q spring-boot:run
```

Login and capture a token:

```bash
curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"agent1","password":"agent1"}'
```

Use the token (redact in notes):

```bash
curl -s http://localhost:8080/api/customers/CUS-1001 \
  -H "Authorization: Bearer <token>" \
  -H "X-Correlation-Id: lab-request-001"
```

Role checks:

1. `agent1` token: `/api/customers/CUS-1001` -> `200`, `/api/admin/ping` -> `403`
2. `admin1` token: `/api/customers/CUS-1001` -> `200`, `/api/admin/ping` -> `200`

Run regression tests:

```bash
mvn -B test
```