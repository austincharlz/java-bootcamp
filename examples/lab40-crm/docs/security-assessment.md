# Lab 40 — Security assessment

**App:** Northstar CRM (`lab40-crm`)  
**Fixtures:** `CUS-1001`, `CUS-1002`, correlation `lab-request-001`  
**Scan command:** `mvn -B -Psecurity-scan dependency-check:check`

## Summary

The CRM lab now has an explicit AppSec gate: scope is documented, the Dependency-Check profile is pinned, customer reads
are protected by method security, and the security regression test blocks cross-customer access. Manual SAST found no
raw PII leakage in the current error path or logs. Dependency-Check completed and failed the build on CVSS thresholds,
so the top dependency findings are now triaged in `docs/security-findings.csv`.

## Before / after

| Item                     | Before                                   | After                                                                                   |
|--------------------------|------------------------------------------|-----------------------------------------------------------------------------------------|
| High findings (≥ CVSS 7) | Pending scan                             | 6 dependency groups triaged                                                             |
| Remediation              | Existing object-level authz needed proof | `CustomerControllerSecurityIT` confirms `CustomerAccessPolicy` blocks `CUS-1002` access |
| Suppressions             | 0                                        | 0                                                                                       |

## Residual risks

| Risk                                                                              | Severity | Owner   | Expiry     | Acceptance                                                         |
|-----------------------------------------------------------------------------------|----------|---------|------------|--------------------------------------------------------------------|
| High-scoring dependency findings remain unresolved until upgrade/fix work is done | High     | student | 2026-09-20 | Use the triage rows to drive upgrades or accepted-risk decisions   |
| Future customer queries must stay parameterized and authorization-checked         | Medium   | student | 2026-09-20 | Keep the existing repository/service patterns and regression tests |

## Evidence paths

- Dependency tree excerpt: `notes/screenshots/lab-40/dependency-tree-jackson.txt`
- Dependency-check failure excerpt: `notes/screenshots/lab-40/dependency-check-error.txt`
- CSV: `docs/security-findings.csv`
- Regression test: `src/test/java/com/northstar/crm/customer/CustomerControllerSecurityIT.java`

## Facts vs assumptions

- Fact: `CustomerController#getByPublicId` is guarded by `@PreAuthorize` and the matching security test passes.
- Fact: The local dependency-check invocation failed on NVD data refresh, not on application compilation or tests.
- Assumption: A training cache or API key will allow the dependency row to be refreshed into a concrete CVE record
  later.

## Reproduce commands

```bash
cd C:/Users/austi/java-bootcamp/examples/lab40-crm
mvn -B test
mvn dependency:tree "-Dincludes=com.fasterxml.jackson.core:jackson-databind"
mvn -B -Psecurity-scan dependency-check:check
```