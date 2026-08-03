# Lab 18 — When to Keep Real Validator

Mock CustomerRepository — I/O boundary.
Keep pure validator real if deterministic and fast.
Mock notifier to avoid email/IO.
Rule: mock I/O and unstable deps; keep pure domain helpers real when cheap.

## Scope
Pre-lab only.