# Security Notes (Lab 28)

## Timed-path token format

This lab uses a stub token format:

```text
lab.<subject>.<role>.<hex(JWT_SECRET.hashCode())>
```

The filter accepts only `lab` tokens with the expected signature and a role of `AGENT` or `ADMIN`.

## Production hardening checklist

1. Replace stub token logic with real JWT verification against IdP-issued tokens.
2. Validate issuer, audience, expiry, and signature via JWKS.
3. Store signing and app secrets in a key vault; never in source control.
4. Rotate keys/secrets and enforce token TTL + refresh strategy.
5. Keep BCrypt/strong password policy and never store plaintext passwords.
6. Never log bearer tokens, passwords, or raw auth headers.

## Failure experiments

| Experiment | Expected observation | Restore |
| --- | --- | --- |
| Set mismatched `JWT_SECRET` between issue/parse environments | Protected routes return `401` | Align `JWT_SECRET` and restart |
| Login with wrong password or send malformed bearer | `401` with safe error path | Use valid credentials/token format |
| Call `/api/admin/ping` as `agent1` | `403` (authorization failure) | Use admin token or correct route |
| Tamper token signature segment | `401` | Use untampered token |