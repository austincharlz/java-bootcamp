# Lab 40 — Map CRM Attack Surfaces

## Reference

| Surface                 | OWASP theme               | Example              |
|-------------------------|---------------------------|----------------------|
| Customer GET/PUT API    | Broken access control     | Agent reads CUS-1001 |
| Search query params     | Injection                 | Name/email filters   |
| pom.xml deps            | Vulnerable components     | Transitive CVE       |
| application.yml secrets | Security misconfiguration | DB password in Git   |
| Actuator endpoints      | Security misconfiguration | Unprotected /env     |

## Step 1 — Inventory touchpoints

In notes, list at least five surfaces for the Spring CRM that serves agents looking up `CUS-1001` (Amina Khan) and
`CUS-1002` (Ravi Singh): HTTP APIs, JWT/RBAC, SQL/JPA, file/log sinks, and (later) Kafka. Mark which hold PII vs IDs.

### Inventory

| Surface                         | PII or IDs                          | OWASP theme                           | Notes                                                                      |
|---------------------------------|-------------------------------------|---------------------------------------|----------------------------------------------------------------------------|
| Customer GET/POST/PUT API       | PII + IDs                           | Broken access control                 | Exposes customer records and must only allow the right agent scope.        |
| JWT/RBAC layer                  | IDs                                 | Broken access control                 | Incorrect role checks can let an agent read another tenant's customer.     |
| SQL/JPA repositories            | PII + IDs                           | Injection                             | Query params and derived lookups must stay parameterized.                  |
| application.yml / .env / config | Secrets + IDs                       | Security misconfiguration             | DB credentials and endpoints must stay out of Git and public logs.         |
| Logs / error handling           | PII + IDs                           | Logging/monitoring failures           | Stack traces and request payloads can leak names, emails, or customer IDs. |
| Build dependencies in pom.xml   | None directly, but affects all data | Vulnerable components                 | A transitive CVE can expose the whole service even if the code is correct. |
| Kafka events (later)            | PII + IDs                           | Broken access control / data exposure | Event payloads can leak customer data if topics are too broad.             |

## Step 2 — Check the reference

Compare your list to OWASP themes: injection, broken access control, security misconfiguration, vulnerable components,
logging/monitoring failures.

### OWASP comparison

- **Injection:** search/filter query params and JPA queries must be parameterized.
- **Broken access control:** customer APIs, JWT/RBAC, and future Kafka consumers need strict scope checks.
- **Security misconfiguration:** `application.yml`, `.env`, and actuator exposure are high risk if secrets or admin
  endpoints are public.
- **Vulnerable components:** dependency drift in `pom.xml` can introduce CVEs before any code runs.
- **Logging/monitoring failures:** logs and stack traces can leak PII even when the API is authenticated.

## Step 3 — Rank top three

Pick the three highest-risk surfaces for a release gate before containers. Write one sentence of business impact per
item.

### Top three risks

1. **Customer API + RBAC** — If an agent can read or edit another customer's record, the app leaks PII and creates
   direct business and compliance impact.
2. **SQL/JPA query paths** — A bad query or unparameterized lookup can expose or corrupt customer data across the whole
   tenant set.
3. **Config and secret handling** — A password committed in `application.yml`, `.env`, or logs lets anyone connect to
   production data and bypass the app entirely.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.