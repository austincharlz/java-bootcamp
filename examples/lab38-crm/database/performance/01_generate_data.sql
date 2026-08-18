SET
search_path TO crm_app;

BEGIN;

INSERT INTO customer (public_id, full_name, email_normalized, phone, status)
VALUES ('CUS-1001', 'Amina Khan', 'amina@example.com', '+1-555-0101', 'ACTIVE'),
       ('CUS-1002', 'Ravi Singh', 'ravi@example.com', '+1-555-0102', 'PROSPECT') ON CONFLICT (public_id) DO NOTHING;

INSERT INTO customer (public_id, full_name, email_normalized, phone, status)
SELECT 'CUS-BULK-' || LPAD(gs::text, 6, '0')          AS public_id,
       'Customer ' || gs                              AS full_name,
       'user' || gs || '@example.test'                AS email_normalized,
       '+1-555-' || LPAD((100000 + gs)::text, 6, '0') AS phone,
       CASE
           WHEN MOD(gs, 10) < 7 THEN 'ACTIVE'
           ELSE 'PROSPECT'
           END                                        AS status
FROM generate_series(1, 50000) AS gs ON CONFLICT (public_id) DO NOTHING;

COMMIT;
