package com.northstar.crm.service;

import com.northstar.crm.dto.CustomerRequest;
import com.northstar.crm.dto.CustomerResponse;
import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomerService {
    private final Map<String, Customer> customersById = new HashMap<>();
    private final String correlationId;

    public CustomerService() {
        this.correlationId = UUID.randomUUID().toString();
    }

    /**
     * DTO adapter: creates a customer from request and returns response.
     * Later labs will populate CustomerRequest with fields.
     */
    public CustomerResponse create(CustomerRequest request) {
        // Stub: CustomerRequest is currently empty. Expand as needed.
        return new CustomerResponse();
    }

    /**
     * DTO adapter: retrieves a customer by ID and returns response.
     * Later labs will populate CustomerResponse with fields.
     */
    public CustomerResponse getById(String customerId) {
        // Stub: CustomerResponse is currently empty. Expand as needed.
        requireNonBlank(customerId, "customerId");
        requireExisting(customerId);
        return new CustomerResponse();
    }

    /**
     * Creates a new customer with the provided details.
     * Throws IllegalArgumentException if required fields are invalid or ID already exists.
     */
    public Customer createCustomer(String customerId, String fullName, String email,
                                   String phone, CustomerStatus status) {
        requireNonBlank(customerId, "customerId");
        requireNonBlank(fullName, "fullName");
        requireUniqueId(customerId);

        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setFullName(fullName);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setStatus(status != null ? status : CustomerStatus.PROSPECT);
        customer.setCreatedAt(LocalDateTime.now());

        customersById.put(customerId, customer);
        return customer;
    }

    /**
     * Retrieves a customer by ID.
     * Throws IllegalArgumentException if customer not found.
     */
    public Customer getCustomer(String customerId) {
        requireNonBlank(customerId, "customerId");
        return requireExisting(customerId);
    }

    /**
     * Updates the status of an existing customer.
     * Throws IllegalArgumentException if customer not found.
     */
    public Customer updateStatus(String customerId, CustomerStatus newStatus) {
        requireNonBlank(customerId, "customerId");
        Customer customer = requireExisting(customerId);
        customer.setStatus(newStatus);
        return customer;
    }

    /**
     * Validates that a string value is not null or blank.
     * Throws IllegalArgumentException if validation fails.
     */
    private void requireNonBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or blank");
        }
    }

    /**
     * Validates that the customer ID is not already in use.
     * Throws IllegalStateException if ID already exists (state violation).
     */
    private void requireUniqueId(String customerId) {
        if (customersById.containsKey(customerId)) {
            throw new IllegalStateException("Customer ID '" + customerId + "' already exists");
        }
    }

    /**
     * Validates that a customer exists with the given ID.
     * Throws IllegalArgumentException if customer not found.
     * Includes correlation ID for tracing.
     */
    private Customer requireExisting(String customerId) {
        Customer customer = customersById.get(customerId);
        if (customer == null) {
            throw new IllegalArgumentException(
                    "Customer ID '" + customerId + "' not found [correlation: " + correlationId + "]");
        }
        return customer;
    }
}