# Lab 27 — Transaction Boundary Placement

Place: TransferService.transfer(...) with @Transactional
Avoid: @Transactional on controller
Why: proxy on Spring service bean; HTTP stays thin
Self-invocation warning: this.transfer() inside same class skips proxy

## Scope
Pre-lab only.