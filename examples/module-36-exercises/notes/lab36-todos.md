# Lab 36 — Fill Route Guard TODOs

## Step 1 — Paste

Create `notes/lab36-todos.md`:

```bash
function RequireAuth({ children }: { children: React.ReactNode }) {
  const token = getAccessToken(); // read from your chosen storage
  if (!token) {
    return <Navigate to="/login" replace />;
  }
  return <>{children}</>;
}

// TODO: attach Authorization: Bearer lab-token-001 (fake) on API fetch (Lab 35+36)
// TODO: never log full token; log correlation lab-request-001 instead
```