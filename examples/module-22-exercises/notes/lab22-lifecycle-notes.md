# Lab 22 — Bean Lifecycle Callbacks

## Lifecycle order
Create -> Inject -> @PostConstruct -> Use -> @PreDestroy

## @PostConstruct purpose
CustomerService: Log init once

## @PreDestroy purpose
CustomerService: log destroy on context close

## What not to do in init
Do not create CUS-1001 inside @PostConstruct for every request.

## Scope
Pre-lab only.