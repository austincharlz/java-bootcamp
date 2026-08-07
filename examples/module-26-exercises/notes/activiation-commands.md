# Lab 26 — Activation Command Drill

## -D / Maven run (dev)
mvn -B spring-boot:run -Dspring-boot.run.profiles=dev

## Env activation (your OS)
$env:SPRING_PROFILES_ACTIVE='dev'

## Tests (test profile)
mvn -B test -Dspring.profiles.active=test

## Scope
Pre-lab only.