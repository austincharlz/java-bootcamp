# Lab 40 — Sketch Findings Triage CSV

## Reference

| Classification | Meaning                              |
|----------------|--------------------------------------|
| true_positive  | Confirm and fix or accept with owner |
| false_positive | Document CPE/path mismatch           |
| accepted_risk  | Time-bounded, owned                  |
| fixed          | Re-scan evidence required            |

## Step 1 — Columns

CSV headers:

finding_id,cve,cvss,dependency,path,classification,owner,due_date,notes

## Step 2 — Check the reference

Classifications: `true_positive`, `false_positive`, `accepted_risk`, `fixed`. Accepted risk needs owner + expiry.

## Step 3 — Sample rows

Synthetic rows for a pre-lab triage sketch (not real production CVEs):

```csv
finding_id,cve,cvss,dependency,path,classification,owner,due_date,notes
SEC-4401,GEN-SYN-0001,7.8,org.apache.commons:commons-text,dependency/commons-text-1.9.jar,true_positive,security-team,2026-09-15,"Transitive JAR in API service path; review upgrade and regression test impact."
SEC-4402,GEN-SYN-0002,0.0,net.minidev:json-smart,dependency/json-smart-2.4.8.jar,false_positive,app-team,2026-08-30,"CPE/path mismatch; not reachable in the CRM API runtime path for agents opening CUS-1001 profiles."
```

## Step 4 — CRM link

A `true_positive` on the API layer could still matter to agents opening `CUS-1001` customer profiles, because a
vulnerable dependency in the request path can create a broader attack surface even before any remediation is planned or
applied.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.