#### Commands (PowerShell)

```bash
mvn -q test
mvn -q clean verify   # includes JaCoCo check
```

#### Coverage Goal

- Minimum 80% line coverage

#### Copilot Review Policy

- Treat AI output as untrusted until human-reviewed and verified.
- Always review line-by-line.
- Use fake/sample data for tests, never production data or identifiable data.
- Reject or rewrite code that looks copied verbatim until approved.
- Tests and suggestions must be validated by humans before merging.

#### Coverage Gap

- Adding the tests found in CustomerValidatorParamterizedTest.java pushed it over 80%. 
