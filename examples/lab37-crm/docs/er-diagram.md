```mermaid
erDiagram
    CUSTOMER ||--o{ ACCOUNT: owns
    CUSTOMER ||--o{ ADDRESS: has
    CUSTOMER ||--o{ STATUS_HISTORY: records

    CUSTOMER {
        bigint customer_id PK
        string public_id UK
        string email UK
        string status
    }

    ACCOUNT {
        bigint account_id PK
        bigint customer_id FK
        string account_number UK
        bigint balance_cents
    }

    ADDRESS {
        bigint address_id PK
        bigint customer_id FK
        string address_type
    }

    STATUS_HISTORY {
        bigint history_id PK
        bigint customer_id FK
        string old_status
        string new_status
    }
```

```bash
customer_id        — PostgreSQL identity surrogate PK
public_id          — immutable business id (CUS-1001)
email              — unique lookup (case-normalized in app or SQL)
account_number     — unique business account identifier
status_history     — append-only audit trail for status changes
```

