package com.northstar.crm.service;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import com.northstar.crm.repository.InMemoryCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerValidatorTest {
    CustomerValidator validator;
    InMemoryCustomerRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryCustomerRepository();
        validator = new CustomerValidator(repo);
    }

    @Test
    void allowsProspectToActive() {
        var repo = new InMemoryCustomerRepository();
        var validator = new CustomerValidator(repo);
        assertDoesNotThrow(() ->
                validator.validateTransition(
                        CustomerStatus.PROSPECT, CustomerStatus.ACTIVE, "lab-request-001"));
    }

    @Test
    void rejectsActiveToProspect() {
        var validator = new CustomerValidator(new InMemoryCustomerRepository());
        assertThrows(IllegalStateException.class, () ->
                validator.validateTransition(
                        CustomerStatus.ACTIVE, CustomerStatus.PROSPECT, "lab-request-001"));
    }
}