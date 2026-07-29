# AI test/refactor notes — Lab 11

## lab11-001 — generated exploratory test
- Initial generated test was a good one, so I used an example of a trivial one
```bash
@Test
void findByStatusReturnsCustomersWithMatchingStatus() {
    Customer active1 = new Customer("CUS-2001", "Alice", "alice@example.com",
            "555-0201", CustomerStatus.ACTIVE, LocalDateTime.now());
    Customer active2 = new Customer("CUS-2002", "Bob", "bob@example.com",
            "555-0202", CustomerStatus.ACTIVE, LocalDateTime.now());
    Customer prospect = new Customer("CUS-2003", "Carol", "carol@example.com",
            "555-0203", CustomerStatus.PROSPECT, LocalDateTime.now());
    
    service.addCustomer(active1);
    service.addCustomer(active2);
    service.addCustomer(prospect);
    
    assertEquals(2, service.findByStatus(CustomerStatus.ACTIVE).size());
    assertEquals(1, service.findByStatus(CustomerStatus.PROSPECT).size());
}
```
- Trivial Test
```bash
@Test
void serviceIsNotNull() {
    assertNotNull(service);
}
```
-  This is a trivial test that checks if the service is not null. It doesn't test any functionality of the service, but it can be useful to ensure that the service is properly initialized before running other tests.
## lab11-002 — CustomerServiceTest
- Smell: Duplicated error handling in findByCustomerId calls
- Refactor: Extracted findOrThrow() private method to centralize "No such customer" error handling
- Test Coverage: CustomerServiceTest: updateStatusThrowsForUnknownCustomer() + new mock test updateStatusCallsNotifier() verifies notifier is called with correct old/new status

## lab11-003 — CustomerNotifier extract + Mockito
- Copilot responded with a table with 6 methods listed: addCustomer(), create(), getById(), listAll(), findByStatus(), updateStatus(), findByCustomerId(). For every one, it provided one missing test coverage. It also provided a high-priority note for addCustomer() for blank/null checks.
- The list of missing tests were pretty well-made and would be useful to implement.
- That gap is not acceptable now. 
## lab11-004 — coverage gaps / acceptance guidelines
Acceptance guidelines for AI-generated tests and refactors:
1. Every assertion must be able to fail — if I can't describe an input that
   breaks it, it isn't a real test.
2. Every refactor must be backed by a passing test suite run before and after.
3. No accepted suggestion may introduce a dependency not already in pom.xml.
4. I can explain, without re-reading Copilot's explanation, why the code
   is correct.
5. Coverage gaps are documented, not silently ignored.