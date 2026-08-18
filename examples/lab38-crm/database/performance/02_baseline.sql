-- EXPLAIN (ANALYZE, BUFFERS) email lookup for a known address
-- EXPLAIN list ACTIVE customers ORDER BY created_at, customer_id LIMIT 50 OFFSET 0
EXEC DBMS_STATS.GATHER_TABLE_STATS(USER, 'CUSTOMER', cascade => TRUE);
EXEC DBMS_STATS.GATHER_TABLE_STATS(USER, 'ACCOUNT', cascade => TRUE);

SELECT table_name, num_rows, last_analyzed
FROM user_tables
WHERE table_name IN ('CUSTOMER', 'ACCOUNT');

SELECT COUNT(*) AS cnt, status
FROM customer
GROUP BY status
ORDER BY status;

-- email bind: user1@example.test
-- public_id bind: CUS-1001
-- status bind: ACTIVE