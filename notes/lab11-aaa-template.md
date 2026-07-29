# Lab 11 AAA template — activate Ravi

## Test name (style only)
activate_prospectRavi_setsStatusActive

## AAA

| Phase | What you write |
| ----- | -------------- |
| Arrange | Customer CUS-1002 Ravi Singh status PROSPECT |
| Act | Call updateStatus / activate so Ravi becomes ACTIVE (conceptual — no full suite yet) |
| Assert | Status is ACTIVE; note correlation lab-request-001 for later logging |

## Boundary
Full Mockito isolation waits for Lab 18. This file is structure only.

## Prep note
Prepare for Lab 11; do not complete the full AI-assisted test suite now.