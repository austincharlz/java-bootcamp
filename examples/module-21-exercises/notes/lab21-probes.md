# Lab 21 — Liveness vs Readiness

## Liveness
Process stuck -> restart (e.g., deadlocked threads)

## Readiness
Dependency down -> not ready, keep process

## Wrong mix
Restarting on transient DB outage

## Lab expectation
Toggle CrmReadinessIndicator OUT_OF_SERVICE; liveness stays UP

## Scope
Pre-lab only.