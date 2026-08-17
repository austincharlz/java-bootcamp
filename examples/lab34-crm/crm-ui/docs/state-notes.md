# Lab 34 — State notes

## Lifted state

`App` owns `customers`, `query`, `mode`, `draft`, and `errors` so list, search, create, edit, and cancel behavior all
read/write one source of truth. Keeping mode in `App` with a discriminated union prevents impossible UI combinations
like creating and editing at the same time.

## Derived filtering

`visible` is derived during render from `customers` and `query`. We intentionally avoid storing filtered state in
`useState` or writing it in `useEffect` to prevent duplicate state, extra renders, and stale/looping behavior.

## Validation

Client validation blocks invalid saves for required name/email and email format, and field errors are shown with
accessible `role="alert"` output. This is UX validation only; API-side validation is still required in Lab 35.
