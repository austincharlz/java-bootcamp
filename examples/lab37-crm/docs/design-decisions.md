# Lab 37 — Design decisions

## public_id vs surrogate key

The CRM needs both a technical key and an immutable business identifier.

- `customer_id` is the PostgreSQL surrogate primary key used for internal joins, foreign keys, and stable row identity.
- `public_id` such as `CUS-1001` is the business-facing identifier exposed to users and APIs. It is intentionally
  immutable so customer references remain stable even if other profile attributes change.
- Using only `email` as the key would be fragile because an email can change; using only a surrogate would make external
  references less readable and harder to reconcile with operational workflows.

## Constraints

The schema protects integrity with named constraints:

- `UNIQUE (public_id)` prevents duplicate customer identifiers.
- `UNIQUE (email)` prevents duplicate customer records from sharing the same mailbox.
- `CHECK (status IN ('PROSPECT', 'ACTIVE', 'CLOSED'))` keeps the lifecycle status limited to the approved states for
  this lab.
- `account.customer_id` references `customer(customer_id)` to ensure every account belongs to a real customer.
- `UNIQUE (account_number)` ensures each account has a distinct operational account identifier.
- `CHECK (balance_cents >= 0)` prevents negative account balances in the timed-path model.

## Delete Rules

- `RESTRICT` is the safer parent delete rule for `customer -> account` because an account is a meaningful business
  record that should not disappear silently when the customer record would be deleted.
- `CASCADE` is reserved for child tables that are purely dependent and can be removed with the parent. In this lab,
  history and dependent records are kept for traceability instead of silently deleting them.
- History is never updated in place because the CRM must preserve an audit trail. Each status change becomes a new row
  in the history table so old and new states remain visible and explainable.

## Cardinality summary

Customer 1 ---- 0..* Account Customer 1 ---- 0..* Address Customer 1 ---- 0..* StatusHistory

This supports Ravi as a `PROSPECT` without any account, while Amina can be `ACTIVE` with one or more accounts and
addresses.