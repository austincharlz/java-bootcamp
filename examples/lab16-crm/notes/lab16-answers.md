#### Failure Experiments
| # | Experiment | Observe | Restore / conclude |
| - | ---------- | ------- | ------------------ |
| 1 | Repository throws bare `RuntimeException` | Generic 500; no internal message in JSON | Keep `fromUnexpected` safe |
| 2 | Blank `fullName` + bad email together | `errors` has both fields | Keep LinkedHashMap aggregation |
| 3 | Not-found twice for `CUS-9999` | Stable 404 shape | Document correlation per-request policy |
#### Reflection Questions
1. The decision to use a centralized exception handling approach with BusinessException and GlobalExceptionHandler had the biggest impact on correctness because it kept error responses consistent and prevented internal exceptions from leaking details.2. The successful test runs showed 404 not-found responses, 409 business conflict responses, 400 validation responses with multiple field errors, and confirmed that failed transitions did not change the customer status.
2. The evidence was the successful Maven runs showing the expected 404, 409, and 400 responses, including correct correlation IDs, validation fields, and confirmation that invalid status transitions did not change the customer state.
3. The hardest failure to diagnose was when exceptions were still being thrown as IllegalStateException instead of BusinessException, because the code looked correct but caused the API to return the wrong error handling path.