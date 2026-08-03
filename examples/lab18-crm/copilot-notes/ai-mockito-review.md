#### Review Checklist
1. Did it mock the class under test? Reject if yes.
   - No, only CustomerRepository is mocked.
2. Are stubs minimal (no unused when)?
   - Yes, only existsById() and existsByEmail() are stubbed.
3. Does verification match the real validator call order?
   - The test verifies both calls, but does not have a strict order. For now, the validator calls existsById() then existsByEmail().
4. Any Thread.sleep or real DB?
    - No, neither was used.
5. Run mvn -q test after accepting?
   - Test ran successfully.