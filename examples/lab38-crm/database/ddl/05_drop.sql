SET
search_path TO crm_app;

-- Reset for clean re-run (lab only)
DROP TABLE IF EXISTS customer_status_history CASCADE;
DROP TABLE IF EXISTS address CASCADE;
DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS customer CASCADE;