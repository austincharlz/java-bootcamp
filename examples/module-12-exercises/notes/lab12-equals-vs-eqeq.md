# Lab 12 — Equals vs ==

## Reference

| Check | Use | Why |
| --- | --- | --- |
| status ACTIVE? | Objects.equals / enum | String identity is unsafe |
| same Customer instance? | == | Reference equality only |
| id CUS-1001? | equals | Value equality |

## Step 2 — Bad snippet

`if (status == "ACTIVE")`
Fails because this compares objected references instead of the string value.

## Step 3 — Good snippet

`if ("ACTIVE".equals(status)) {
    // Amina is ACTIVE
}`

## Step 4 — JDK note

Note: prefer enums on JDK 21 sketches when status set is closed.

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.