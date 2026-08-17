package com.northstar.crm.service;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerNotifierMockTest {

    @Test
    void createAndUpdateStatusWithTypedAPI() {
        CustomerService service = new CustomerService();
        
        // Create a customer
        Customer created = service.createCustomer("CUS-1002", "Ravi Singh", 
                "ravi.singh@example.com", "555-0102", CustomerStatus.PROSPECT);
        
        assertNotNull(created);
        assertEquals("CUS-1002", created.getCustomerId());
        assertEquals("Ravi Singh", created.getFullName());
        assertEquals(CustomerStatus.PROSPECT, created.getStatus());
        
        // Update status
        Customer updated = service.updateStatus("CUS-1002", CustomerStatus.ACTIVE);
        assertEquals(CustomerStatus.ACTIVE, updated.getStatus());
        
        // Retrieve and verify
        Customer retrieved = service.getCustomer("CUS-1002");
        assertEquals(CustomerStatus.ACTIVE, retrieved.getStatus());
    }

    @Test
    void createCustomerWithDuplicateIdThrows() {
        CustomerService service = new CustomerService();
        service.createCustomer("CUS-1002", "Ravi Singh", "ravi.singh@example.com", 
                "555-0102", CustomerStatus.PROSPECT);
        
        assertThrows(IllegalStateException.class, () ->
            service.createCustomer("CUS-1002", "Someone Else", "other@example.com", 
                    "555-9999", CustomerStatus.PROSPECT)
        );
    }

    @Test
    void getCustomerWithInvalidIdThrows() {
        CustomerService service = new CustomerService();
        
        assertThrows(IllegalArgumentException.class, () -> service.getCustomer("NONEXISTENT"));
    }
}