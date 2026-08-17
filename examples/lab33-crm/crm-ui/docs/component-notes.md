# Component Notes (Lab 33)

## Key decisions

1. `CustomerList` uses `customer.customerId` as the React key to prevent identity bugs during sort/filter/reorder.
2. `StatusBadge` always renders readable status text (`Prospect`, `Active`, `Closed`) so meaning does not depend on
   color.
3. `CustomerForm` is presentational and parent-controlled with label associations, field-level alerts, and explicit
   submit/cancel callbacks.

## Accessibility notes

1. `CustomerCard` is an `<article>` with `aria-labelledby` linked to the customer heading.
2. The customer email is a semantic `mailto:` link.
3. Form labels use `htmlFor` + `id`, and validation messages use `role="alert"` with `aria-describedby`.

## Lab 34 handoff

1. Keep these components pure and reusable.
2. Lift server state and network orchestration into `App` (or a container) without changing component markup contracts.
