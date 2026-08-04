#### Unit vs Integration (IT) vs UI scope
- Unit: fast, single-class behavior; use Mockito to isolate repositories/validators. Run everywhere.
- Integration (IT): SpringBootTest slices (RANDOM_PORT) verifying controllers, beans, and REST I/O (TestRestTemplate). Slower; run in pipeline stage after unit tests.
- UI: Selenium/WebDriver tests against static UI surface (customers.html). Run headless in CI; keep selector stability using data-testid attributes.

#### Headless CI strategy
- Use WebDriverManager to fetch chromedriver; run Chrome with "--headless=new" and fixed window size.
- Set implicit wait to 0; prefer explicit WebDriverWait for deterministic timing.
- Add selenium-devtools matching CDP if CDP warnings affect stability.

#### Correlation header
- Tests and UI post requests send X-Correlation-Id: lab-request-001 to enable traceability in logs and responses.