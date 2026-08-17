# Lab 13 - SOAP Contract Design (No Server Implementation)

## Overview

Lab 13 focuses on understanding SOAP service contracts through the Customer Management domain. This lab defines the
complete WSDL, XML Schema, and sample messages for three operations: CreateCustomer, GetCustomer, and UpdateCustomer. It
also demonstrates SOAP fault contracts for error scenarios.

**Important**: Lab 13 does NOT implement a Spring-WS server. It is a contract-first design phase. Implementation details
will be covered in later labs (e.g., Lab 24 with Spring-WS).

## Contents

### Contracts

- **CustomerService.wsdl** - Complete service definition with port types, bindings, and service endpoints
- **customer.xsd** - XML Schema defining request/response types and CustomerType structure

### Samples

All sample messages use the SOAP 1.1 envelope format with namespace `http://northstar.com/crm/customer`.

#### Operations

1. **CreateCustomer**
    - `createCustomerRequest.xml` - Create a new customer (Amina Khan, ACTIVE)
    - `createCustomerResponse.xml` - Response with assigned CUS-1001 ID and timestamp

2. **GetCustomer**
    - `getCustomerRequest.xml` - Retrieve customer CUS-1002 with correlation ID
    - `getCustomerResponse.xml` - Response with Ravi Patel in PROSPECT status

3. **UpdateCustomer**
    - `updateCustomerRequest.xml` - Elevate Ravi (CUS-1002) from PROSPECT to ACTIVE
    - `updateCustomerResponse.xml` - Full CustomerType with updated status

#### Faults

1. **fault-customerNotFound.xml** - Client fault for non-existent customer (CUS-9999)
    - Error code: `CUSTOMER_NOT_FOUND`
    - Includes correlation ID for request tracing

2. **fault-validation.xml** - Client fault for blank fullName
    - Error code: `VALIDATION_ERROR`
    - Field-level detail for validation failures

### Documentation

- **soap-design-notes.md** - Design notes, step-by-step breakdown, and cross-walk to Lab 12
- **operation-matrix.md** - Quick reference for operation signatures

## Key Concepts

### SOAP Fault Structure

Lab 13 introduces proper fault handling following SOAP 1.1 specification:

- **faultcode**: Distinguishes Client faults (validation, missing data) from Server faults (internal errors)
- **faultstring**: Human-readable message including correlation ID
- **detail**: Machine-readable error codes and structured fault information

### Correlation IDs

Request messages include `correlationId` for end-to-end tracing across async systems and logs. Faults echo the
correlation ID to maintain request/response correlation.

### Cross-walk with Lab 12

Lab 12's `CustomerService.updateStatus(customerId, newStatus)` method maps directly to the UpdateCustomer SOAP
operation. Lab 13 wraps this business logic in an Envelope/Body/Fault structure to expose it as a remote service
contract.

## Lab Progression

- **Lab 12**: Core business logic (in-memory repository, updateStatus method)
- **Lab 13** (this lab): SOAP contract design, samples, and fault definitions
- **Lab 24**: Spring-WS server implementation with exception handlers for faults

## No Spring-WS Implementation

This lab intentionally stops at the contract layer. Partners can review and validate the SOAP service interface before
implementation begins. This supports contract-first development and service versioning.
