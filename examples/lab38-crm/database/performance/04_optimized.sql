-- replace DATE_TRUNC/TRUNC(created_at) filters with half-open tstz range
-- keyset page: WHERE (created_at, customer_id) < ($ts, $id) ORDER BY ... LIMIT 50
-- compare nested loop vs hash join hints/plans for customer→account

-- Sargable half-open range
SELECT customer_id
FROM customer
WHERE created_at >= TIMESTAMP '2026-07-01 00:00:00'
  AND created_at < TIMESTAMP '2026-07-02 00:00:00';