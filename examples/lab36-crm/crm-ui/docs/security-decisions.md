#### Security Decisions

**Assets**
- Customer PII in the CRM list and forms
- Access tokens or session state used to call the API
- Any session cookie if the app is ever run in cookie mode

**Browser inputs**
- Login credentials
- Search text
- Customer form fields
- Customer names and other data returned by the API

**Trust boundaries**
- The browser is untrusted
- Spring Security / the API is the authorization boundary
- Persistent web storage is not a safe place for bearer tokens
- Route guards improve UX only and do not replace backend authorization

**Attacker goals**
- Steal tokens through XSS
- Trigger CSRF in cookie mode
- Abuse open redirects after login
- Confuse the UI into showing protected data without a valid session

**Mapped controls**
- Keep tokens in memory only
- Attach bearer headers only to the CRM API origin
- Render customer text through JSX escaping only
- Use an explicit auth state with a `checking` phase
- Clear auth state on 401 responses, but not on 403 responses
- Use generic login errors to avoid account enumeration
- CSRF is N/A for the current bearer-token mode because browsers do not attach Authorization headers automatically
- Add CSRF controls only when the app uses cookies
- Serve login and API traffic over HTTPS in production; enable HSTS there
- Production hosts should send CSP, nosniff, and referrer-policy headers

- NOTE: Route guards are not authorization.