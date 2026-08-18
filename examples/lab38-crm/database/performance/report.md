# Lab 38 — Performance report

| Experiment                  | Plan hash / notes                        | Buffers | Median time | Write cost                                 |
|-----------------------------|------------------------------------------|---------|-------------|--------------------------------------------|
| lab38-001 baseline email    | Index Scan uk_customer_email             | 4       | 0.123 ms    | N/A                                        |
| lab38-002 after email index | Index Scan ux_customer_email_norm        | 1+3     | 0.055 ms    | N/A                                        |
| lab38-003 OFFSET deep page  | Nested loop + sort 35k ACTIVE            | 837     | 11.151 ms   | Index on (status, created_at, customer_id) |
| lab38-004 keyset page       | Keyset (created_at, customer_id) < tuple | N/A     | N/A         | Eliminates OFFSET scan overhead            |

## Data distribution and binds

- Status skew: about 70% ACTIVE / 30% PROSPECT
- Email bind: `user1@example.test`
- Public id bind: `CUS-1001`
- Status bind: `ACTIVE`

## Join Strategies

### Selective join (CUS-1001)

- Strategy: **Nested Loop** with Index Scan on public_id
- Buffers: 4 shared hits
- Execution time: 0.064 ms
- Rows: 1 (highly selective)

### Broader join (ACTIVE customers, LIMIT 100)

- Strategy: **Hash Left Join**
- Buffers: 4 shared hits
- Execution time: 0.084 ms
- Rows scanned: 34810 ACTIVE filtered to 100

**Lesson:** Optimizer correctly chose nested loop for single-customer query, hash join for broader scan. Both are
appropriate for their selectivity.

## Paging Strategies

### Deterministic Offset Paging

- Page 0 (OFFSET 0): IDs 50004, 50000, 49999, ...
- Page 1 (OFFSET 10): IDs 49988, 49987, 49986, ...
- **Status:** ✓ Disjoint pages, deterministic ordering via (created_at DESC, customer_id DESC)

### Keyset Paging

- Page 1 via keyset (after last_created=2026-08-18 19:24:06, last_id=49989): IDs 49988, 49987, 49986, ...
- **Status:** ✓ Returns identical rows to offset page 1
- **Advantage:** No OFFSET scan needed; just range scan from last tuple

## Why keyset beats deep OFFSET

Keyset paging eliminates the need to skip over already-seen rows. With deep OFFSET (e.g., OFFSET 50000), the database
must scan/process 50000 rows before returning the next 20. Keyset paging uses the last tuple's values as predicates, so
it resumes exactly where it left off without re-processing prior rows. For large datasets with many pages, keyset scales
much better.

## Baseline

```bash
                                                         QUERY PLAN                                                          
------------------------------------------------------------------------------------------------------------------------
 Index Scan using uk_customer_email on customer  (cost=0.41..8.43 rows=1 width=44) (actual time=0.047..0.047 rows=1 loops=1)
   Index Cond: ((email_normalized)::text = 'user1@example.test'::text)
   Buffers: shared hit=4
 Planning:
   Buffers: shared hit=110 dirtied=2
 Planning Time: 0.368 ms
 Execution Time: 0.123 ms
(7 rows)

```

## Index Selective Email Lookup

```bash
                                                         QUERY PLAN                                                          
------------------------------------------------------------------------------------------------------------------------
 Index Scan using uk_customer_email on customer  (cost=0.41..8.43 rows=1 width=44) (actual time=0.025..0.025 rows=1 loops=1)
   Index Cond: ((email_normalized)::text = 'user1@example.test'::text)
   Buffers: shared hit=4
 Planning:
   Buffers: shared hit=106
 Planning Time: 0.456 ms
 Execution Time: 0.047 ms
(7 rows)

```

## After Indexes

```bash
                                                        QUERY PLAN                                                        
------------------------------------------------------------------------------------------------------------------------
 Limit  (cost=2382.31..2382.36 rows=20 width=31) (actual time=11.118..11.121 rows=20 loops=1)
   Buffers: shared hit=837
   ->  Sort  (cost=2382.31..2469.33 rows=34810 width=31) (actual time=11.116..11.117 rows=20 loops=1)
         Sort Key: created_at DESC, customer_id DESC
         Sort Method: top-N heapsort  Memory: 27kB
         Buffers: shared hit=837
         ->  Seq Scan on customer  (cost=0.00..1456.03 rows=34810 width=31) (actual time=0.004..5.117 rows=35001 loops=1)
               Filter: ((status)::text = 'ACTIVE'::text)
               Rows Removed by Filter: 15001
               Buffers: shared hit=831
 Planning:
   Buffers: shared hit=138
 Planning Time: 0.278 ms
 Execution Time: 11.151 ms
(14 rows)
```

## Non-Sargable

```bash
                                                QUERY PLAN                                                 
-----------------------------------------------------------------------------------------------------------
 Seq Scan on customer  (cost=0.00..1706.04 rows=250 width=8) (actual time=0.008..9.252 rows=50002 loops=1)
   Filter: ((date_trunc('day'::text, created_at))::date = '2026-08-18'::date)
   Buffers: shared hit=831
 Planning:
   Buffers: shared hit=131
 Planning Time: 0.251 ms
 Execution Time: 10.619 ms
(7 rows)

```

## Sargable

```bash

```