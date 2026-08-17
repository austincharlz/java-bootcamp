package com.northstar.crm.service;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import com.northstar.crm.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceDuplicateEmailTest {

    @Mock CustomerRepository repository;

    private CustomerValidator validator;
    private DefaultCustomerService service;

    @BeforeEach
    void setUp() {
        validator = new CustomerValidator(repository);
        service = new DefaultCustomerService(repository, validator);
    }

    @Test
    void addCustomer_throwsWhenEmailDuplicate() {
        // Fixture: CUS-1001 Amina Khan
        Customer amina = new Customer();
        amina.setCustomerId("CUS-1001");
        amina.setFullName("Amina Khan");
        amina.setEmail("amina.khan@example.com");
        amina.setStatus(CustomerStatus.PROSPECT);

        // Repository behavior: id does not exist, but email already exists
        when(repository.existsById("CUS-1001")).thenReturn(false);
        when(repository.existsByEmail("amina.khan@example.com")).thenReturn(true);

        // Expect a validation failure due to duplicate email
        assertThrows(IllegalStateException.class, () -> service.addCustomer(amina));

        // Verify repository checks and that save was never invoked
        verify(repository).existsById("CUS-1001");
        verify(repository).existsByEmail("amina.khan@example.com");
        verify(repository, never()).save(any(Customer.class));
    }
}
