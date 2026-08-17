# CRM UI (Lab 33)

React + TypeScript UI shell for the Customer Management Platform.

## Runbook

```bash
npm run dev
npm run test -- --run
npm run build
```

## What is implemented

- Typed models and fixtures (`Amina Khan`, `Ravi Singh`)
- Presentational components:
    - `StatusBadge`
    - `CustomerCard`
    - `CustomerList`
    - `CustomerForm`
    - `AppLayout`, `CustomerToolbar`, `LoadingState`, `ErrorState`, `EmptyState`
- RTL tests for `CustomerList` behavior and edit callback contract

## Reflection Questions

1. The biggest correctness decision was using customer.customerId as the React key (never array index), because it
   preserves component identity during reorder/filter and prevents the wrong card state/actions from being reused.
2. Evidence is the passing behavior tests (CustomerList.test.tsx) that verify Amina/Ravi rendering, empty-state
   behavior, and Edit emitting CUS-1001, plus successful npm run build and repeated npm run test -- --run passes.
3. The hardest failure was diagnosing why tests initially “failed” due to no discovered test files; the fix was
   realizing setup/config was fine but the project simply lacked actual .test.tsx files until they were added.
