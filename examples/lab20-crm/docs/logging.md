# Logging contract

## Structured pattern

The CRM service uses the following Logback pattern for console output:

```text
%d{ISO8601} %-5level [%thread] %logger{36} corr=%X{corr} cust=%X{cust} op=%X{op} - %msg%n
```

## MDC keys

- `corr`: request correlation ID (default `lab-request-001`)
- `cust`: customer ID for the current operation
- `op`: operation name (`create`, `get`, or `changeStatus`)

## Logging rules

- Log customer identifiers only (`CUS-1001`, `CUS-1002`)
- Never log full names, emails, phone numbers, addresses, passwords, tokens, or PAN values
- Use reason codes for validation failures such as `reason=missing_full_name`
- Keep the filter responsible for `corr` MDC lifecycle; services should set operation-scoped MDC values only

## Safe examples

```text
INFO create customer id=CUS-1001
WARN Rejecting create reason=missing_full_name customerId=CUS-1002
INFO get customer id=CUS-1001
```
