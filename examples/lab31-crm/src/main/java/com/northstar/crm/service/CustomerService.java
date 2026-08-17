package com.northstar.crm.service;

import com.northstar.crm.dto.CustomerRequest;
import com.northstar.crm.event.CustomerEvent;
import com.northstar.crm.event.CustomerEventPublisher;
import com.northstar.crm.model.Customer;
import java.time.Instant;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CustomerService {
    private final Map<String, Customer> store = new ConcurrentHashMap<>();
    private final CustomerEventPublisher eventPublisher;

    public CustomerService(CustomerEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        store.put("CUS-1001", Customer.amina());
        store.put("CUS-1002", Customer.ravi());
    }

    public Customer create(CustomerRequest request, String correlationId) {
        if (store.containsKey(request.getId())) {
            throw new IllegalStateException("Duplicate customer: " + request.getId());
        }
        Customer c = new Customer(request.getId(), request.getName(), request.getEmail(), request.getStatus());
        store.put(c.getId(), c);
        eventPublisher.publish(toEvent("CustomerCreated", c, correlationId));
        return c;
    }

    public Customer get(String id) {
        Customer c = store.get(id);
        if (c == null) throw new IllegalArgumentException("Customer not found: " + id);
        return c;
    }

    public Customer updateStatus(String id, String status, String correlationId) {
        Customer c = get(id);
        c.setStatus(status);
        eventPublisher.publish(toEvent("CustomerStatusChanged", c, correlationId));
        return c;
    }

    private CustomerEvent toEvent(String eventType, Customer customer, String correlationId) {
        String effectiveCorrelationId = (correlationId == null || correlationId.isBlank())
                ? "lab-request-001"
                : correlationId;
        return new CustomerEvent(
                UUID.randomUUID().toString(),
                eventType,
                1,
                Instant.now(),
                customer.getId(),
                effectiveCorrelationId,
                "lab31-crm",
                new CustomerEvent.CustomerData(customer.getName(), customer.getStatus()));
    }
}