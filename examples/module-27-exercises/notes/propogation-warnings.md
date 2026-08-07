# Lab 27 — Propagation Warnings

| Pattern | Risk |
| --- | --- |
| REQUIRES_NEW on log only | Log commits; money rolls back |
| Self-invocation | @Transactional ignored |
| Catch Exception and swallow | No rollback |
| @Transactional on controller | Wrong boundary |

Lab default: REQUIRED on TransferService.transfer

## Scope
Pre-lab only.