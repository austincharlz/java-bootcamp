#### Step 1
| Dependency need | Scope               |
| --------------- |---------------------|
| JUnit Jupiter used only in `src/test/java` | `test`              |
| Spring Context API called from production sources (Lab 9 learning placeholder) | `compile` (default) |
| JDBC driver you never import in Java source but need at runtime later | `runtime`           |
| API the application server will provide in production | `provided`           |
#### Step 3
- JUnit becomes a production dependency: it is packaged/resolved for the main app, pollutes the runtime classpath, and signals the wrong intent to teammates and CI.
#### Step 4
- Test libraries always use `<scope>test</scope>`.
- Do not leave JUnit on the default `compile` scope.