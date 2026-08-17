# lab34-crm-ui runbook

## Small summary

This lab lifts CRM UI state into `App` and implements controlled search/form inputs, immutable create/edit updates, safe
cancel/reset behavior, client-side validation, and render-time filtering without derived-state effects. It also adds a
title-sync `useEffect` and interaction tests that cover the main user flows.

## Commands

```bash
npm run dev
npm run test -- --run
npm run build
```

## Reflection Questions

1. Using a single discriminated `mode` state (`list | create | edit`) in `App` had the biggest impact because it
   prevents invalid UI states and keeps transitions explicit. Deriving `visible` during render instead of storing
   filtered state also removed stale-data and loop risks.

2. Interaction tests cover seed rendering, search filtering, valid create, invalid create validation, edit save, cancel
   behavior, and empty-state rendering. Build output is also green, showing the TypeScript/Vite app compiles cleanly
   with the final state logic.

3. The trickiest issue was form validation not appearing in tests because browser native validation intercepted submit
   behavior. Adding `noValidate` to the form let app-level validation run consistently and fixed the failing flow.
