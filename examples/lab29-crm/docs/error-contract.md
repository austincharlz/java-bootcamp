# CRM Error Contract (Lab 29)

Customer APIs under `/api/customers/**` require Lab 28 Bearer authentication. All examples and automated checks use a JWT from `POST /api/auth/login` with `agent1/agent1`.

| Exception | HTTP status | `error` | `message` shape |
| --- | --- | --- | --- |
| `MethodArgumentNotValidException` | 400 | `Bad Request` | `Validation failed` with `violations[]` entries `{field,message}` |
| `IllegalArgumentException` | 404 | `Not Found` | Service message (for example `Customer not found: CUS-9999`) |
| `IllegalStateException` | 409 | `Conflict` | Service message (for example `Duplicate customer: CUS-1001`) |
| `Exception` | 500 | `Internal Server Error` | `Unexpected error` (safe client message only) |

Unified contract note: Lab 14 request DTO constraints now enforce input validity at the controller boundary using `@Valid`, and Lab 16-style centralized exception handling maps validation/domain failures into one stable `ErrorResponse` envelope for all customer REST failures.