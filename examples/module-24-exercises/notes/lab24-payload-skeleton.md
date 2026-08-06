# Lab 24 — PayloadRoot Skeleton

## Class annotation
@Endpoint class CustomerEndpoint

## @PayloadRoot localPart
@PayloadRoot(namespace = NAMESPACE, localPart = "GetCustomerRequest")

## Method inputs/outputs
method getCustomer(@RequestPayload GetCustomerRequest req) → map → customerService.get(...) → map response

## Delegation line (words)
NAMESPACE must match customer.xsd targetNamespace.

## Scope
Pre-lab only.