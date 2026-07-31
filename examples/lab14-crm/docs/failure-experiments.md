| # | Experiment | Observe | Restore |
| - | ---------- | ------- | ------- |
| 1 | Remove Hibernate Validator temporarily; run tests | `NoProviderFoundException` / missing provider | Restore dependency |
| 2 | Missing `fullName`, bad email, `status` blank/null | Facade fails before `addCustomer` | Keep validate-first order |
| 3 | Create `CUS-1001` twice | Duplicate = service rule, not Bean Validation | Document difference |
| 4 | Skip `validator.validate` call | Invalid data reaches service | Re-add validate; note risk |
| 5 | Status `Active` (wrong case) | `valueOf` fails at map time | Require enum-aligned strings |