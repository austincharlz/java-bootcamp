package com.northstar.crm.service;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import com.northstar.crm.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceMockitoTest {

    @Mock CustomerRepository repository;

    private CustomerValidator validator;
    private DefaultCustomerService service;

    @BeforeEach
    void setUp() {
        // Validator and service MUST share the same mock repository
        validator = new CustomerValidator(repository);
        service = new DefaultCustomerService(repository, validator);
    }

    @Test
    void placeholderCompiles() {
        assertNotNull(repository);
        assertNotNull(service);
    }

    @Test
    void activatesProspectUsingStubbedRepository() {
        Customer ravi = new Customer("CUS-1002", "Ravi Singh", "ravi.singh@example.com", "",CustomerStatus.PROSPECT, java.time.LocalDateTime.now(), java.time.LocalDateTime.now());

        when(repository.findById("CUS-1002")).thenReturn(Optional.of(ravi));
        when(repository.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));

        Customer result = service.changeStatus(
                "CUS-1002", CustomerStatus.ACTIVE, "lab-request-001");

        assertEquals(CustomerStatus.ACTIVE, result.getStatus());
        verify(repository).findById("CUS-1002");
        verify(repository).save(argThat(c ->
                "CUS-1002".equals(c.getCustomerId()) && c.getStatus() == CustomerStatus.ACTIVE));
        // Prefer explicit verify counts if validator also reads exists*:
        // verify(repository, times(1)).save(...);
    }

    @Test
    void unknownCustomerDoesNotSave() {
        when(repository.findById("CUS-9999")).thenReturn(Optional.empty());

        assertThrows(Exception.class, () ->
                service.changeStatus("CUS-9999", CustomerStatus.ACTIVE, "lab-request-001"));

        verify(repository).findById("CUS-9999");
        verify(repository, never()).save(any());
    }

    @Test
    void addCustomerCapturesSavedEntity() {
        when(repository.existsById("CUS-1001")).thenReturn(false);
        when(repository.existsByEmail("amina.khan@example.com")).thenReturn(false);
        when(repository.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));

        service.addCustomer(new Customer("CUS-1001", "Amina Khan", "amina.khan@example.com", "", CustomerStatus.ACTIVE, java.time.LocalDateTime.now(), java.time.LocalDateTime.now()));

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(repository).save(captor.capture());
        assertEquals("CUS-1001", captor.getValue().getCustomerId());
        assertEquals("Amina Khan", captor.getValue().getFullName());
        assertEquals(CustomerStatus.ACTIVE, captor.getValue().getStatus());
    }
}