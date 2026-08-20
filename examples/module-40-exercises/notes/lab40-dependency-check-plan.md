# Lab 40 — Plan Dependency-Check Gate

## Step 1 — Profile sketch

Write a bullet plan for a Maven profile `-Psecurity-scan`: plugin goal, HTML+JSON reports, and a CVSS fail threshold
placeholder.

### Draft plan

- Add a Maven profile named `security-scan`.
- Bind `org.owasp:dependency-check-maven:check` to the profile.
- Generate both HTML and JSON reports for review and evidence.
- Fail the build on a placeholder CVSS threshold, such as `7.0`.
- Keep the profile off by default so normal builds stay fast.

## Step 2 — Check the reference

Confirm JDK 21 + Maven Wrapper habits: `./mvnw -B -Psecurity-scan dependency-check:check` from the CRM module root.

### Reference check

- Use JDK 21.
- Run from the module root with Maven Wrapper.
- Preferred command shape:

```bash
./mvnw -B -Psecurity-scan dependency-check:check
```

## Step 3 — Suppression policy draft

Write three required fields for any suppression: CVE id, owner, expiry date. State that silent suppressions fail the
gate.

### Suppression policy

- Required fields:
    - CVE id
    - owner
    - expiry date
- Every suppression must include a short justification.
- Silent suppressions or untracked exceptions fail the gate.
- Expired suppressions must be removed or re-approved.

## Step 4 — Folder prep

Create note paths for sanitized HTML/JSON under `notes/screenshots/lab-40/` (do not run the full scan yet unless
instructor says smoke only).

### Output paths

- `notes/screenshots/lab-40/dependency-check-report.html`
- `notes/screenshots/lab-40/dependency-check-report.json`
- Keep these sanitized: no secrets, no raw stack traces, no local credentials.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.