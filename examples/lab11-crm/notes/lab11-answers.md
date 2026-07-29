#### Concepts to Discuss
1. Difference between an exploratory Copilot-generated test and a deliberately designed suite?
2. What makes an assertion “false confidence”?
3. Why extract `CustomerNotifier` before mocking, instead of mocking concrete `CustomerService`?
4. What is a code smell, and which Lab 10 smell is the clearest refactor candidate?
5. Why is high coverage % not the same as meaningful coverage?
6. What regression risk exists when refactoring without a full suite—and how do today’s tests help?
7. When should you trust a Copilot extract-method vs verify manually?
8. What acceptance criteria should a reviewer apply before merging an AI-generated test or refactor?
9. Why keep JUnit/Mockito at `test` scope?
10. How does this preview set up Labs 17–18 without replacing them?
#### Implementation Checkpoints
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
4. Pass
Checkpoint C
1. Pass
2. Pass
3. Pass
4. Pass
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
1. Synthetic IDs like CUS-1001 and CUS-1002 are safe because they contain no real customer data or sensitive information.
2. Human code review, pull requests, and CI checks enforce review before AI-generated changes can merge.
3. It creates false confidence because it passes without proving important business behavior works.
4. The refactor may introduce hidden regressions that are not detected.
5. Real customer data, passwords, API keys, secrets, tokens, or production identifiers must never appear.
6. They would check important workflows, edge cases, failures, and whether tests validate business rules.
7. Mocking isolates tests from email or notification systems, making tests faster and focused on customer behavior.
8. Track AI assistance in pull requests, commits, review comments, and require human approval before merging.
#### Reflection
1. A meaningful test like `findByStatusReturnsCustomersWithMatchingStatus()` has assertions that can actually fail when behavior breaks—it creates multiple customers with different statuses, then verifies the count matches. A false confidence test like `serviceIsNotNull()` always passes if initialization works at all, never validating the service does anything useful.  Tests must exercise business logic paths, not just object existence.
2. Before extraction, the service was tightly coupled to concrete notification logic, making it impossible to isolate the status-change behavior from notification side effects. By extracting a `CustomerNotifier` interface with one method (`notifyStatusChange`), it made the service injectable-testable: tests could pass a mock or stub and verify *what gets called* without triggering real notifications. This transformed `updateStatus` from untestable to fully verifiable.
3. Don't. The lab spelled out five non-negotiable rules: (1) every assertion must be able to fail, (2) refactors need before/after test runs, (3) no new dependencies without review, (4) you must understand the code without re-reading Copilot's explanation, (5) coverage gaps get documented, not hidden. Accepting tests unread violates rules 1 and 4 and creates silent regressions in a shared codebase. Code review is the trust boundary—AI suggestions are untrusted until a human reviews and verifies them.
4. Copilot suggested adding a `save` method to `Customer` after I asked it to "add a save method" with minimal context. It modified `CustomerRepository` instead, adding a HashMap and unexpected imports. I rejected it because it didn't fit the lab scope. The fix was more specific prompts with field names, validation rules, and package context.
5. Lab 11 built the foundation—a layered architecture separating models, DTOs, services, and repositories with clear interfaces. Labs 17–18 will plug Spring Boot, HTTP controllers, and a real database into these interfaces without refactoring the core logic. The `CustomerNotifier` dependency-injection pattern used today is the same pattern Spring will manage via `@Autowired` and `@Bean`. Tests now verify behavior in isolation; Spring tests will verify wiring works end-to-end.
6. The gap is acceptable: `create()` and `getById()` are stubs returning placeholder responses, untested. This is tolerable because Lab 11 is about testing discipline, not complete coverage. Once you add a real database in future labs, that gap becomes unacceptable—every method must have tests that verify database queries, error handling, and data integrity.
7. Lab 11 is week 1 foundation. You built the domain models (`Customer`, `CustomerStatus`), service contracts (`CustomerService`, `CustomerNotifier`), DTOs, and repository interfaces that every subsequent lab depends on. Weeks 2–6 will add REST controllers (Lab 12), database persistence (Lab 13+), authentication, validation, and async workflows—all layered on top of the clean architecture you proved here. Everything downstream assumes the service layer works correctly and is testable.
8. Silent regressions: if you extract `findOrThrow()` without running tests first, you might introduce a bug that only a teammate discovers after pulling your changes, forcing them to debug your code. You lose proof the refactor was safe, making code reviews weaker. Team trust erodes when refactors silently break behavior. The cost is technical (hidden bugs), social (blame and delays), and organizational.
9. The dependency-injection pattern stays foundational.