# Lab 40 — Draft AppSec Go/No-Go Questions

## Step 1 — Questions

1. **High CVE owned?** — Are all high-severity CVEs either patched or have an active remediation plan tracked?
2. **Secrets in Git?** — Have we verified no credentials, API keys, or secrets are committed to the repository?
3. **Authz negative test?** — Do the auth tests cover negative cases (unauthorized access, privilege escalation
   attempts)?
4. **Suppression policy?** — Are all dependency-check suppressions documented with justification and expiration dates?
5. **Verify still green?** — Do all security scans (SAST, dependency-check, auth tests) pass cleanly without silent
   failures?

## Step 2 — Check the reference

Leadership rule baseline:

- No ship on raw scanner volume (suppressions must be justified, not ignored)
- No silent suppressions (all suppressions must be tracked and documented)
- No secrets (hard requirement—secrets in git = auto-reject)

## Step 3 — Tie to CRM

1. **High CVE owned?** — Unpatched CVEs expose the CRM to data breach risk; Amina/Ravi's customer data is at risk.
2. **Secrets in Git?** — Leaked credentials allow attackers to impersonate agents; CRM transactions could be forged.
3. **Authz negative test?** — Missing authorization tests could allow unauthorized access to customer records;
   Amina/Ravi could serve wrong tenants.
4. **Suppression policy?** — Undocumented suppressions hide real vulnerabilities; agents can't trust the scan results.
5. **Verify still green?** — Silent failures mask deployment of broken security; users lose confidence in agent
   integrity.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.