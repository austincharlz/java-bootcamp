# Lab 35 CRM UI Runbook

## Prerequisites

- Node 22+
- Spring CRM API running on `http://localhost:8080`

## Setup

1. Copy `.env` to `.env`.
2. Start the UI:

```bash
npm install
npm run dev
```

## Environment

```text
VITE_API_BASE_URL=http://localhost:8080
```

`VITE_*` values are public and must not contain secrets.

## API Integration Implemented

- Typed API boundary in `src/api/http.ts`
- Normalized `ApiError` (`network | http | abort | parse`)
- Typed customer API operations in `src/api/customers.ts`
- Abortable customer load and explicit loading/empty/data/error states
- Create/update submission with save-lock duplicate prevention
- 400 backend field error mapping to form fields
- Correlation header: `X-Correlation-Id: lab-request-001`

## Spring CORS Configuration (server-side)

Apply this in Spring `WebConfig`:

```java
registry.addMapping("/api/**")
    .allowedOrigins("http://localhost:5173")
    .allowedMethods("GET", "POST", "PUT", "DELETE")
    .allowedHeaders("Content-Type", "Authorization", "X-Correlation-Id");
```

## Verification Commands

```bash
npm run test -- --run
npm run build
curl -i -H "Origin: https://evil.example" http://localhost:8080/api/customers
```

Expected CORS probe result: no `Access-Control-Allow-Origin: https://evil.example`.