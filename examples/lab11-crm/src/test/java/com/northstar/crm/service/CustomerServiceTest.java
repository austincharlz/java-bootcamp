package com.northstar.crm.service;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    private CustomerService service;

    @BeforeEach
    void setUp() {
        service = new CustomerService();
    }

    @Test
    void addCustomerStoresNewCustomer() {
        Customer amina = new Customer("CUS-1001", "Amina Khan", "amina.khan@example.com",
                "555-0101", CustomerStatus.ACTIVE, LocalDateTime.now());
        service.addCustomer(amina);
        assertEquals(1, service.listAll().size());
        assertEquals("CUS-1001", service.listAll().get(0).getCustomerId());
    }

    @Test
    void addCustomerRejectsDuplicateId() {
        Customer amina = new Customer("CUS-1001", "Amina Khan", "amina.khan@example.com",
                "555-0101", CustomerStatus.ACTIVE, LocalDateTime.now());
        service.addCustomer(amina);
        Customer duplicate = new Customer("CUS-1001", "Someone Else", "x@example.com",
                "555-0000", CustomerStatus.PROSPECT, LocalDateTime.now());
        assertThrows(IllegalStateException.class, () -> service.addCustomer(duplicate));
    }

    @Test
    void updateStatusChangesExistingCustomer() {
        Customer ravi = new Customer("CUS-1002", "Ravi Singh", "ravi.singh@example.com",
                "555-0102", CustomerStatus.PROSPECT, LocalDateTime.now());
        service.addCustomer(ravi);
        service.updateStatus("CUS-1002", CustomerStatus.ACTIVE);
        assertEquals(CustomerStatus.ACTIVE,
                service.findByCustomerId("CUS-1002").orElseThrow().getStatus());
    }

    @Test
    void updateStatusThrowsForUnknownCustomer() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateStatus("CUS-9999", CustomerStatus.ACTIVE));
    }

    @Test
    void findByStatusReturnsCustomersWithMatchingStatus() {
        Customer active1 = new Customer("CUS-2001", "Alice", "alice@example.com",
                "555-0201", CustomerStatus.ACTIVE, LocalDateTime.now());
        Customer active2 = new Customer("CUS-2002", "Bob", "bob@example.com",
                "555-0202", CustomerStatus.ACTIVE, LocalDateTime.now());
        Customer prospect = new Customer("CUS-2003", "Carol", "carol@example.com",
                "555-0203", CustomerStatus.PROSPECT, LocalDateTime.now());
        
        service.addCustomer(active1);
        service.addCustomer(active2);
        service.addCustomer(prospect);
        
        assertEquals(2, service.findByStatus(CustomerStatus.ACTIVE).size());
        assertEquals(1, service.findByStatus(CustomerStatus.PROSPECT).size());
    }

    @Test
    void updateStatusCallsNotifier() {
        Customer customer = new Customer("CUS-3001", "David", "david@example.com",
                "555-0301", CustomerStatus.PROSPECT, LocalDateTime.now());
        
        MockCustomerNotifier notifier = new MockCustomerNotifier();
        CustomerService serviceWithNotifier = new CustomerService(notifier);
        
        serviceWithNotifier.addCustomer(customer);
        serviceWithNotifier.updateStatus("CUS-3001", CustomerStatus.ACTIVE);
        
        assertTrue(notifier.wasNotified);
        assertEquals("CUS-3001", notifier.capturedCustomerId);
        assertEquals(CustomerStatus.PROSPECT, notifier.capturedOldStatus);
        assertEquals(CustomerStatus.ACTIVE, notifier.capturedNewStatus);
    }

    static class MockCustomerNotifier implements CustomerNotifier {
        boolean wasNotified = false;
        String capturedCustomerId;
        CustomerStatus capturedOldStatus;
        CustomerStatus capturedNewStatus;

        @Override
        public void notifyStatusChange(String customerId, CustomerStatus oldStatus, CustomerStatus newStatus) {
            wasNotified = true;
            this.capturedCustomerId = customerId;
            this.capturedOldStatus = oldStatus;
            this.capturedNewStatus = newStatus;
        }
    }
}