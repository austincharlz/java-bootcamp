# Lab 27 — Transfer Pseudocode

@Transactional
transfer(from, to, amount, correlation):
load accounts
if to == ACC-FORCE-FAIL: throw
debit from; credit to
write TransactionLog(correlation)
// commit via Spring

## Scope
Pre-lab only.