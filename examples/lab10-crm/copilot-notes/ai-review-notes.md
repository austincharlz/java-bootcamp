#### Concepts to Discuss
1. Difference between a Copilot **inline completion** and a **Copilot Chat** request—when is each better?
2. Why prompt specificity (fields, types, rules) changes enterprise Java output quality vs a vague comment?
3. What is the “trust boundary” between an AI suggestion and code allowed to touch real customer data?
4. Which business rule protects integrity in `Customer` (fixed `CustomerStatus` enum vs free-text `String`)?
5. What happens if Copilot suggests a class/annotation/library not on this project’s classpath?
6. Why must every accepted suggestion be reviewed line-by-line, not only “does it compile”?
7. What is the risk of pasting real customer data or credentials into Copilot Chat?
8. How does license/provenance risk apply to a multi-line AI block, and what if it looks copied from a known OSS project?
9. Why is Copilot **not** a runtime dependency of `customer-service`?
10. How will Lab 11 reuse today’s review discipline when generating tests?
#### Lab Answers
- lab10-001 — weak vs strong (entity)
  - Date: 07/28/2026
  - Weak prompt used: "// Customer class"
  - Output summary: The comment was regenerated below
  - Strong prompt used: "// Java entity class Customer in package com.northstar.crm.entity representing a Northstar CRM customer. Fields: customerId (String, format "CUS-1001"), fullName (String), email (String), phone (String), status (CustomerStatus enum: PROSPECT, ACTIVE, SUSPENDED, CLOSED), createdAt (LocalDateTime). No-args constructor, all-args constructor, getters and setters, equals/hashCode based only on customerId, toString."
  - Output summary: The generated class included all specified fields, constructors, and methods, adhering to the provided format and requirements.
  - Decision: accept / reject / partial
  - Reason (1 sentence): Accepted because the reviewed code was exactly what was requested in the strong prompt, with all fields and methods correctly implemented.
- lab10-002 — weak vs strong (addCustomer)
  - Date: -7/28/2026
  - Weak prompt used: "// add a customer"
  - Output summary: Nothing was generated.
  - Strong prompt used: "// Method addCustomer(Customer customer) on CustomerService: reject if customerId is null/blank, reject if a customer with the same customerId already exists (throw IllegalStateException), otherwise store it in the in-memory list and return it."
  - Output summary: The generated method correctly implements the specified validation logic, throwing exceptions for invalid input and adding valid customers to the in-memory list.
  - Decision: accept / reject / partial
  - Reason (1 sentence): Accepted because the method correctly implemented the prompt. 
- lab10-003 - Mandatory human-review pass
  1. Pass
  2. Pass
  3. Pass
  4. Pass
  5. Pass
- lab10-004
  1. I avoided using the customer's real ID, name, or really anything about them and used made up/fake people with their own data.
  2. If I saw code that was copied verbatim, I would check the source, test the code, and then check on the license of the copied code's source to see if it is allowed.
  3. Never accept, commit, or merge AI-generated code unless at least one developer on the team fully understands how it works, reviews it for correctness, security, and readability, and verifies it with appropriate testing.
#### Failure Experiments
| # | Experiment | Observe                                                        | Restore / conclude                          |
| - | ---------- |----------------------------------------------------------------|---------------------------------------------|
| 1 | Ask Chat to “add a `save` method to `Customer`” with no context | Modified CustomerRepository.java with a new HashMap and imports | Rejected as it did not specifically fit lab |
| 2 | Disable Copilot (or briefly disconnect) and add `deleteCustomer(String)` by hand | You can still finish without AI                                | Took a lot longer and made more mistakes    |
| 3 | Draft (do **not** send) a Chat prompt with a fake SSN/password as “example” | Functionality was similar, but no data leakage                 | Rewrite using only `CUS-1001`/`CUS-1002`    |
| 4 | Ask Chat to “build the entire CRM service layer” in one shot | Oversized, hard-to-review dump                                 | Prefer scoped prompts from Steps 4–5        |
#### Implemenation Checkpoints
Checkpoint A
1. Pass
2. Pass
3. Pass
Checkpoint B
1. Pass
2. Pass
3. Pass
Checkpoint C
1. Pass
2. Pass
3. Pass
Checkpoint D
1. Pass
2. Pass
3. Pass
#### Manual Verification
1. Pass
2. Pass
3. Pass
4. Pass
5. Pass
6. Pass
7. Pass
8. Pass
9. Pass
10. Pass
#### Security and Production Review
1. Everything in the prompt is untrusted because it could be wrong. Only your team's business rules, coding standards, and requirements are trusted.
2. Human review happens during the pull request before the code is merged. A reviewer checks the code, runs tests, and approves it. 
3. Never share any type of real world customer data, credentials, or secrets. Never share passwords, API keys, tokens, etc. Always use placeholders when needed. 
4. Boilerplate code, comments, and test are usually regenerated. Actual business logic, security code, and important design decisions should be written by and, at least, checked and reviewed by a human. 
5. Trust the CI build because it is the official build that has been tried and tested. 
6. They would check code quality, tests, security, and compliance with coding standards. They would also verify that the code meets the requirements and does not introduce any vulnerabilities.
7. Large copied code may have copyright or licensing issues. Always review carefully and rewrite as needed. 
8. Use comments, commit messages, pull requests notes, and documentation to keep track of changes. Record what has been reviewed and approved by humans.
#### Reflection
1. One dangerous suggestion was Copilot removing input validation from a method. I noticed it during code review because it could have caused invalid data or crashes. I kept the validation and tested the code to make sure it worked correctly.
2. Adding more specific requirements to the prompt gave better results. Instead of asking for a method, I included the expected behavior, edge cases, and coding style. This produced code that needed fewer changes.
3. I reviewed every AI suggestion, tested the code, and only kept the parts I understood. I also verified that all unit tests and the CI build passed before merging. Any unclear or incorrect suggestions were rewritten or rejected.