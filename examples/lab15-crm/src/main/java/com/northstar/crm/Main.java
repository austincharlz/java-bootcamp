package com.northstar.crm;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import com.northstar.crm.repository.CustomerRepository;
import com.northstar.crm.repository.InMemoryCustomerRepository;
import com.northstar.crm.service.CustomerService;
import com.northstar.crm.service.CustomerValidator;
import com.northstar.crm.service.DefaultCustomerService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Shared repository instance for validator + service
        CustomerRepository repo = new InMemoryCustomerRepository();
        CustomerValidator validator = new CustomerValidator(repo);
        CustomerService service = new DefaultCustomerService(repo, validator);

        // Add Amina (ACTIVE)
        Customer amina = new Customer(
                "CUS-1001",
                "Amina Khan",
                "amina@example.com",
                "555-1001",
                CustomerStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Add Ravi (PROSPECT)
        Customer ravi = new Customer(
                "CUS-1002",
                "Ravi Singh",
                "ravi@example.com",
                "555-1002",
                CustomerStatus.PROSPECT,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        service.addCustomer(amina);
        service.addCustomer(ravi);

        // Change Ravi PROSPECT -> ACTIVE
        Customer activatedRavi = service.changeStatus(
                "CUS-1002",
                CustomerStatus.ACTIVE,
                "lab-request-001"
        );

        System.out.println("Ravi updated status: " + activatedRavi.getStatus());

        // Attempt  ACTIVE -> PROSPECT transition on Amina
        try {
            service.changeStatus("CUS-1001", CustomerStatus.ACTIVE, "lab-request-001");
        } catch (IllegalStateException ex) {
            System.out.println("expected failure: " + ex.getMessage());
        }
        System.out.println("CUS-1001 still: " + service.findById("CUS-1001").orElseThrow().getStatus());

        // Verify Amina remains ACTIVE
        Customer currentAmina = service.findById("CUS-1001")
                .orElseThrow();

        System.out.println("Amina current status: " + currentAmina.getStatus());
    }
}