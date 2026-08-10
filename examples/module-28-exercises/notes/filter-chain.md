# Lab 28 — SecurityFilterChain Sketch

## Session policy
Session: STATELESS

## Login matcher
/api/auth/login → permitAll

## Customers matcher + roles
/api/customers/** → hasAnyRole(AGENT, ADMIN)

## Admin matcher + roles
/api/admin/** → hasRole(ADMIN)

## Other
Other APIs → authenticated (default deny extras)

## Scope
Pre-lab only.