# Lab 40: Application Security Testing for the CRM

## Summary

This lab implements an AppSec gate for the Northstar CRM backend: scope mapped to OWASP attack surfaces,
Dependency-Check configured with a pinned version, customer-read access controlled by object-level authorization with a
regression test, and a triage ledger linking findings to owners and due dates. The scan completed and identified 6
high-risk dependency groups; triaged findings are documented in `docs/security-findings.csv` with residual-risk
acceptance criteria in `docs/security-assessment.md`.

## Quick Start

```bash
cd C:/Users/austi/java-bootcamp/examples/lab40-crm

# Set NVD API key for dependency scanning
$env:NVD_API_KEY = "your-real-key"

# Build and test
mvn -B clean verify
mvn -B test

# Run security scan
mvn -B -Psecurity-scan dependency-check:check

# View scan reports
# - HTML: target/dependency-check-report.html
# - JSON: target/dependency-check-report.json

# Review findings
cat docs/security-findings.csv
cat docs/security-assessment.md
```

## Artifacts

| File                                                                         | Purpose                                            |
|------------------------------------------------------------------------------|----------------------------------------------------|
| `docs/threat-checklist.md`                                                   | Scope and OWASP surface mapping                    |
| `docs/security-findings.csv`                                                 | Triage ledger (dependency-check + manual SAST)     |
| `docs/security-assessment.md`                                                | Gate narrative, residual risks, reproduce commands |
| `dependency-check-suppressions.xml`                                          | Time-bounded CVE suppressions (none added yet)     |
| `src/test/java/com/northstar/crm/customer/CustomerControllerSecurityIT.java` | Object-level authorization regression test         |

## Security and Production Review

### Untrusted inputs

- HTTP request parameters: `@RequestParam` in `CustomerController`
- Request body: `@RequestBody CreateCustomerRequest`
- Headers: `X-Correlation-Id` for logging (not used for security decisions)
- Path variables: `{publicId}` in `GET /api/customers/{publicId}`

### Where authn/authz/validation enforced

- **Method security:** `@PreAuthorize("@customerAccessPolicy.canReadCustomer(...)")` on endpoint
- **Access policy:** `CustomerAccessPolicy.canReadCustomer()` enforces object-level ownership (authenticated username
  must match the public customer ID)
- **Input validation:** `@Valid` on `CreateCustomerRequest`; paging parameters sanitized in controller
- **Repository queries:** JPA-derived methods; no raw SQL concatenation

### Sensitive data storage

- Customer PII (name, email) stored in PostgreSQL database with unique email constraint
- Error responses via `ApiExceptionHandler` return generic `ProblemDetail` text only; no raw PII in logs
- `.env` file holds database credentials and NVD API key (not committed)
- Correlation ID in responses is synthetic, not customer data

## Reflection Questions

1. `CustomerAccessPolicy` enforcement via `@PreAuthorize` combined with a regression test that explicitly denies
   cross-customer reads proved that object-level authorization works. Without the test, the gate would rely only on code
   review, which is weaker.

2. `CustomerControllerSecurityIT.agentCannotReadAnotherAgentsCustomer()` test passes, asserting HTTP 403 when an agent
   (CUS-1001) tries to read a different customer (CUS-1002). The test also verifies `verifyNoInteractions(service)`,
   proving the authorization check blocks the request before the service layer is reached.

3. Dependency-Check version incompatibility with the NVD API key passing took longer to troubleshoot than the CVEs
   themselves because the error message initially suggested an authentication problem rather than a plugin configuration
   issue. Once the version was corrected, the scan completed and the real high-risk dependencies appeared immediately.