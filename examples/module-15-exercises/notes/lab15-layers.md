# Lab 15 — Layer Diagram

## Step 1 — Boxes
```bash
+-------------------+
|    API Adapter    |
+-------------------+
          |
          | activate(CUS-1002)
          | correlation: lab-request-001
          v
+-------------------+
| CustomerService   |
+-------------------+
          |
          | request customer data
          v
+------------------------+
| CustomerRepository    |
+------------------------+
          |
          | Customer returned
          v
+-------------------+
| CustomerService   |
+-------------------+
          |
          | Customer returned outward
          v
+-------------------+
|    API Adapter    |
+-------------------+
```
## Step 2 — Arrow labels

Label activate(CUS-1002) flowing inward; Customer returned outward.

## Step 3 — Correlation

Note: lab-request-001 crosses the API edge into service logging later.

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.