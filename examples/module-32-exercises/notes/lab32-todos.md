# Lab 32 — Fill Resilience TODOs

## Step 1 — Paste

Create `notes/lab32-todos.md`:

## Step 2 - Fill 
```bash
@CircuitBreaker(name = "accountProfile", fallbackMethod = "profileFallback")
@Retry(name = "accountProfile")
@TimeLimiter(name = "accountProfile")
public CompletableFuture<AccountProfile> getProfile(String customerId) {
  return accountClient.fetch(customerId); // remote client
}

private CompletableFuture<AccountProfile> profileFallback(String customerId, Throwable t) {
  // TODO: return minimal profile for CUS-1001 / CUS-1002
  return CompletableFuture.completedFuture(AccountProfile.minimal(customerId));
}
```