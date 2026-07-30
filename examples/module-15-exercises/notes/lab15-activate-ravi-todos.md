# Lab 15 — Fill Activate Ravi Pseudocode TODOs

## Step 1 — Copy pseudocode
- customer = repo.findById(CUS-1002)
- if customer is null → throw NotFound
- if status is not PROSPECT → throw IllegalState/domain exception
- set status to ACTIVE
- repo.save(customer)
- log correlation: lab-request-001
- 
## Step 2 — Fill blanks

Fill with CUS-1002, NotFound, PROSPECT, IllegalState/domain exception, ACTIVE, save/update, lab-request-001.

## Step 3 — Repo boundary note

*Repository saves state; it does not decide PROSPECT→ACTIVE.*

## Step 4 — Self-check

Confirm Ravi starts PROSPECT and ends ACTIVE in the filled sheet.

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.