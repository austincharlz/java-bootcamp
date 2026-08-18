SET
search_path TO crm_app;

SELECT public_id, full_name, email_normalized, status
FROM customer
ORDER BY public_id;

SELECT c.public_id, a.account_number, a.balance
FROM customer c
         LEFT JOIN account a ON a.customer_id = c.customer_id
ORDER BY c.public_id;

SELECT c.public_id, h.old_status, h.new_status, h.reason, h.correlation_id
FROM customer_status_history h
         JOIN customer c ON c.customer_id = h.customer_id
ORDER BY h.changed_at;

BEGIN;

SAVEPOINT negative_test;
INSERT INTO customer (public_id, full_name, email_normalized, status)
VALUES ('CUS-X', 'Bad Status', 'bad@example.com', 'UNKNOWN');
ROLLBACK TO SAVEPOINT negative_test;

SAVEPOINT negative_test;
INSERT INTO customer (public_id, full_name, email_normalized, status)
VALUES ('CUS-DUPE', 'Dupe', 'amina@example.com', 'PROSPECT');
ROLLBACK TO SAVEPOINT negative_test;

SAVEPOINT negative_test;
INSERT INTO account (account_number, customer_id, account_type, balance)
VALUES ('ACCT-ORPHAN', 999999, 'CHECKING', 0);
ROLLBACK TO SAVEPOINT negative_test;

COMMIT;
