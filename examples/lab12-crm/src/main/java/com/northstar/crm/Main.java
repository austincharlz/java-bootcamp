package com.northstar.crm;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import com.northstar.crm.service.CustomerService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Lab 12 — refactor CustomerService (doStuff → clean API)\n");
        
        CustomerService service = new CustomerService();
        
        // Create CUS-1001
        System.out.println("=== Creating customers ===");
        Customer amina = service.createCustomer("CUS-1001", "Amina Khan", 
                "amina.khan@example.com", "555-0001", CustomerStatus.ACTIVE);
        System.out.println("Created: " + amina.getCustomerId() + " | " + amina.getFullName() +
                " | Status: " + amina.getStatus());

        // Create CUS-1002
        Customer ravi = service.createCustomer("CUS-1002", "Ravi Singh", 
                "ravi.singh@example.com", "555-0002", CustomerStatus.PROSPECT);
        System.out.println("Created: " + ravi.getCustomerId() + " | " + ravi.getFullName() +
                " | Status: " + ravi.getStatus());
        
        // Get customers
        System.out.println("\n=== Retrieving customers ===");
        Customer retrieved1 = service.getCustomer("CUS-1001");
        System.out.println("Retrieved: " + retrieved1.getCustomerId() + " | " + retrieved1.getFullName());

        Customer retrieved2 = service.getCustomer("CUS-1002");
        System.out.println("Retrieved: " + retrieved2.getCustomerId() + " | Status: " + retrieved2.getStatus());
        
        // Update status
        System.out.println("\n=== Updating customer status ===");
        Customer updated = service.updateStatus("CUS-1002", CustomerStatus.ACTIVE);
        System.out.println("Updated: " + updated.getCustomerId() + " | Status: " + updated.getStatus());
        
        // Try duplicate (should fail)
        System.out.println("\n=== Testing error handling ===");
        try {
            service.createCustomer("CUS-1001", "Another Person", "other@example.com", 
                    "555-9999", CustomerStatus.PROSPECT);
            System.out.println("Duplicate creation should have failed!");
        } catch (IllegalStateException e) {
            System.out.println("Duplicate rejected: " + e.getMessage());
        }
        
        // Try unknown customer (should fail)
        try {
            service.getCustomer("CUS-9999");
            System.out.println("Unknown customer lookup should have failed!");
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown ID rejected: " + e.getMessage());
        }
        
        System.out.println("\nAll demos completed successfully!");
    }
}