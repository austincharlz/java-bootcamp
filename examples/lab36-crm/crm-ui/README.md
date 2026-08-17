# Lab 36 CRM UI

Frontend security lab for the CRM SPA. This version uses in-memory auth state, origin-scoped bearer headers, a guarded
login flow, 401 logout handling, and XSS-safe rendering.

## Run

```bash
npm install
npm run dev
npm run test -- --run
npm run build
```

## Notes

- Tokens stay in memory only.
- Bearer headers are attached only to the CRM API origin.
- `/login` rejects external return URLs.
- `ProtectedRoute` is UX-only; the API remains the authorization boundary.