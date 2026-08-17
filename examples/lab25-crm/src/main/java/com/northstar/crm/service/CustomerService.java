package com.northstar.crm.service;

import com.northstar.crm.model.Customer;
import com.northstar.crm.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(Customer customer, String correlationId) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer is required");
        }
        if (customer.getId() == null || customer.getId().isBlank()) {
            throw new IllegalArgumentException("Customer id is required");
        }

        if (customerRepository.existsById(customer.getId())) {
            throw new IllegalStateException("Duplicate customer");
        }

        customerRepository.save(customer);
        log.info("Created customer {} [correlationId={}]", customer.getId(), correlationId);
        return customer;
    }

    public Customer get(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
    }

    public List<Customer> list() {
        return customerRepository.findAll();
    }
}