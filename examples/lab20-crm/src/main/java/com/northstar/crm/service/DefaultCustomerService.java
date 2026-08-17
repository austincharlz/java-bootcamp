package com.northstar.crm.service;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import com.northstar.crm.exception.BusinessException;
import com.northstar.crm.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultCustomerService implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCustomerService.class);
    private final CustomerRepository repository;
    private final CustomerValidator validator;

    public DefaultCustomerService(
            CustomerRepository repository,
            CustomerValidator validator) {

        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        String customerId = customer.getCustomerId();
        MDC.put("cust", customerId);
        MDC.put("op", "create");
        logger.info("create customer id={}", customerId);
        validator.validateNew(customer, null);
        return repository.save(customer);
    }

    @Override
    public Customer addCustomer(Customer customer, String correlationId) {
        String customerId = customer.getCustomerId();
        MDC.put("cust", customerId);
        MDC.put("op", "create");
        logger.info("create customer id={}", customerId);
        validator.validateNew(customer, correlationId);
        return repository.save(customer);
    }

    @Override
    public Optional<Customer> findById(String customerId) {
        MDC.put("cust", customerId);
        MDC.put("op", "get");
        logger.info("get customer id={}", customerId);
        return repository.findById(customerId);
    }

    @Override
    public List<Customer> listAll() {
        MDC.put("op", "list");
        logger.debug("Listing all customers");
        return List.copyOf(repository.findAll());
    }

    @Override
    public Customer changeStatus(
            String customerId,
            CustomerStatus newStatus,
            String correlationId) {

        MDC.put("cust", customerId);
        MDC.put("op", "changeStatus");
        logger.info("Changing customer status [{}]: {} -> {}", correlationId, customerId, newStatus);
        Customer existing = repository.findById(customerId)
                .orElseThrow(() ->
                        BusinessException.notFound(
                                customerId,
                                correlationId));

        validator.validateTransition(
                existing.getStatus(),
                newStatus,
                correlationId);

        existing.setStatus(newStatus);

        logger.info("Customer status changed successfully [{}]", correlationId);
        return repository.save(existing);
    }
}