# Lab 27 — ACID evidence

| Property | Lab evidence |
| -------- | ------------ |
| Atomicity | Forced transfer to `ACC-FORCE-FAIL` throws HTTP 500 path and leaves `ACC-MAIN-1001` unchanged; failed attempt does not add a `TransactionLog`. |
| Consistency | Successful transfer (`5.00` in `happyPathMovesFunds`) moves the same amount out of MAIN and into LOYALTY, preserving ledger balance rules for this scenario. |
| Isolation | Each transfer runs as one transaction boundary in `TransferService`; concurrent write behavior is managed by the database/JPA transaction isolation level. |
| Durability | Within a running app instance, committed transfers persist and are queryable; because this lab uses in-memory H2 (`jdbc:h2:mem:lab27`), data is not durable across full process shutdown. |