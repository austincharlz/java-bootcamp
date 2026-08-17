package com.northstar.crm.service;

import com.northstar.crm.entity.CustomerStatus;
import com.northstar.crm.exception.BusinessException;
import com.northstar.crm.repository.InMemoryCustomerRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CustomerValidatorParameterizedTest {

    CustomerValidator validator =
            new CustomerValidator(new InMemoryCustomerRepository());

    @ParameterizedTest
    @CsvSource({
            "PROSPECT,ACTIVE",
            "PROSPECT,CLOSED",
            "ACTIVE,SUSPENDED",
            "ACTIVE,CLOSED",
            "SUSPENDED,ACTIVE"
    })
    void legalTransitions(CustomerStatus from, CustomerStatus to) {
        assertDoesNotThrow(() ->
                validator.validateTransition(
                        from,
                        to,
                        "lab-request-001"
                )
        );
    }

    @ParameterizedTest
    @CsvSource({
            "ACTIVE,PROSPECT",
            "CLOSED,ACTIVE",
            "CLOSED,PROSPECT"
    })
    void illegalTransitions(CustomerStatus from, CustomerStatus to) {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> validator.validateTransition(
                        from,
                        to,
                        "lab-request-001"
                )
        );

        assertEquals(
                "BUSINESS_CONFLICT",
                exception.getCode()
        );
    }
}