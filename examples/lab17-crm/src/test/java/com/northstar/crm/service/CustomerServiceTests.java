package com.northstar.crm.service;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import com.northstar.crm.exception.BusinessException;
import com.northstar.crm.repository.InMemoryCustomerRepository;
import com.northstar.crm.service.CustomerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTests {

    DefaultCustomerService service;

    @BeforeEach
    void setUp() {
        InMemoryCustomerRepository repository = new InMemoryCustomerRepository();
        CustomerValidator validator = new CustomerValidator(repository);

        service = new DefaultCustomerService(repository, validator);
    }

    private Customer createCustomer(
            String id,
            String name,
            String email,
            String phone,
            CustomerStatus status
    ) {
        LocalDateTime now = LocalDateTime.now();

        return new Customer(
                id,
                name,
                email,
                phone,
                status,
                now,
                now
        );
    }

    @Test
    void addAndActivateRaviHappyPath() {
        Customer amina = createCustomer(
                "CUS-1001",
                "Amina Khan",
                "amina@example.com",
                "555-1111",
                CustomerStatus.ACTIVE
        );

        Customer ravi = createCustomer(
                "CUS-1002",
                "Ravi Singh",
                "ravi@example.com",
                "555-2222",
                CustomerStatus.PROSPECT
        );

        service.addCustomer(amina);
        service.addCustomer(ravi);

        service.changeStatus(
                "CUS-1002",
                CustomerStatus.ACTIVE,
                "lab-request-001"
        );

        Customer updated = service.findById("CUS-1002").orElseThrow();

        assertNotNull(updated);
        assertEquals(CustomerStatus.ACTIVE, updated.getStatus());
    }

    @Test
    void addAndFindAmina() {
        Customer amina = createCustomer(
                "CUS-1001",
                "Amina Khan",
                "amina@example.com",
                "555-1111",
                CustomerStatus.ACTIVE
        );

        service.addCustomer(amina);

        Customer found = service.findById("CUS-1001").orElseThrow();
        assertEquals("Amina Khan", found.getFullName());
    }

    @Test
    void duplicateCustomerIdThrowsIllegalState() {
        Customer first = createCustomer(
                "CUS-1001",
                "Amina Khan",
                "amina@example.com",
                "555-1111",
                CustomerStatus.ACTIVE
        );

        Customer second = createCustomer(
                "CUS-1001",
                "Amina Alias",
                "amina.alias@example.com",
                "555-9999",
                CustomerStatus.PROSPECT
        );

        service.addCustomer(first);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> service.addCustomer(second)
        );

        assertTrue(exception.getMessage().contains("duplicate customerId"));
    }

    @Test
    void duplicateEmailThrowsIllegalState() {
        Customer first = createCustomer(
                "CUS-2001",
                "Alice",
                "same@example.com",
                "555-0001",
                CustomerStatus.ACTIVE
        );

        Customer second = createCustomer(
                "CUS-2002",
                "Bob",
                "same@example.com",
                "555-0002",
                CustomerStatus.PROSPECT
        );

        service.addCustomer(first);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> service.addCustomer(second)
        );

        assertTrue(exception.getMessage().contains("duplicate email"));
    }

    @Test
    void illegalTransitionThrowsConflict() {
        Customer amina = createCustomer(
                "CUS-1001",
                "Amina Khan",
                "amina@example.com",
                "555-1111",
                CustomerStatus.ACTIVE
        );

        service.addCustomer(amina);

        String correlation = "lab-request-001";
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.changeStatus(
                        "CUS-1001",
                        CustomerStatus.PROSPECT,
                        correlation
                )
        );

        assertEquals("BUSINESS_CONFLICT", exception.getCode());
        assertEquals(correlation, exception.getCorrelationId());
        assertTrue(exception.getMessage().contains("illegal status transition"));

        Customer unchanged = service.findById("CUS-1001").orElseThrow();
        assertEquals(CustomerStatus.ACTIVE, unchanged.getStatus());
    }

    @Test
    void missingCustomerThrowsNotFound() {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.changeStatus(
                        "CUS-9999",
                        CustomerStatus.ACTIVE,
                        "lab-request-001"
                )
        );

        assertEquals(
                "CUSTOMER_NOT_FOUND",
                exception.getCode()
        );
    }
}