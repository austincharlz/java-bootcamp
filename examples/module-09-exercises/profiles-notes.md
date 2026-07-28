#### Step 1
| Question | Your answer |
| -------- | ----------- |
| Which profile is active when you run plain `mvn package`? | `dev` (`activeByDefault`) |
| How do you activate `prod` on the command line? | `mvn -Pprod …` (example: `mvn -Pprod package`) |
| What is the `app.env` value under `dev`? | `dev` |
| What is the `app.env` value under `prod`? | `prod` |
#### Step 3
- putting production database passwords inside the `dev` profile;
  - This exposes sensitive information to anyone who can access the project.
- making `prod` `activeByDefault` on every engineer laptop;
  - This means that the production profile will be default. Accidentally running code against production systems can cause major issues. 
- assuming profiles change Java package names (they do not — they change build/config properties);
  - Maven profiles do not rename Java packages or classes. They only change build settings and configuration, such as dependencies, properties, or plugins.
- documenting secrets in screenshots of profile properties.
  - Screenshots containing passwords, API keys, or other secrets are easy to share accidentally and difficult to remove once distributed.
#### Step 4
- Keep `dev` as the laptop default.
- Activate `prod` intentionally with `-Pprod`.
- Never store real production secrets in `pom.xml` profiles.