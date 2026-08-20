# Lab 40 — Threat checklist (OWASP-aligned)

**Scope:** CRM API serving synthetic fixtures `CUS-1001` / `CUS-1002`.

## Surfaces

| Surface                | OWASP theme               | Risk note                                                                                                                 | Status   |
|------------------------|---------------------------|---------------------------------------------------------------------------------------------------------------------------|----------|
| Customer lookup by ID  | Broken access control     | `@PreAuthorize("@customerAccessPolicy.canReadCustomer(...)")` + `CustomerControllerSecurityIT` block cross-customer reads | Verified |
| Search / filter params | Injection                 | Paging uses allowed sort fields and repository-derived queries; no string concatenation                                   | Verified |
| Logs / error bodies    | Sensitive data exposure   | `ApiExceptionHandler` returns generic `ProblemDetail` text and a synthetic correlation id                                 | Verified |
| Dependencies           | Vulnerable components     | `dependency-check` profile is pinned; top runtime findings are triaged in `security-findings.csv`                         | Verified |
| Secrets in config      | Security misconfiguration | `application.yml` stays env-backed; no secrets committed in Git                                                           | Verified |

## Notes

- `lab40-001` maps to the dependency triage row in `docs/security-findings.csv`.
- `lab40-002` and `lab40-003` document the SAST checks for access control and data exposure.
- Never paste NVD API keys, tokens, or real emails into this file.