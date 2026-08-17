package com.northstar.crm;

import com.northstar.crm.api.CustomerApiFacade;
import com.northstar.crm.dto.CustomerRequestDTO;
import com.northstar.crm.dto.CustomerResponseDTO;
import com.northstar.crm.service.CustomerService;

public class Main {

    public static void main(String[] args) {
        System.out.println("Lab 14 — API Facade + DTO validation\n");

        CustomerService service = new CustomerService();
        CustomerApiFacade facade = new CustomerApiFacade(service);

        String correlationId = "lab-request-001";

        System.out.println("=== Creating customers through DTO ===");

        // Create CUS-1001 Amina
        CustomerRequestDTO aminaRequest = new CustomerRequestDTO();
        aminaRequest.setCustomerId("CUS-1001");
        aminaRequest.setFullName("Amina Khan");
        aminaRequest.setEmail("amina.khan@example.com");
        aminaRequest.setStatus("ACTIVE");

        CustomerResponseDTO amina = facade.create(aminaRequest, correlationId);

        System.out.println("Created: "
                + amina.getCustomerId()
                + " | "
                + amina.getFullName()
                + " | Status: "
                + amina.getStatus());


        // Create CUS-1002 Ravi
        CustomerRequestDTO raviRequest = new CustomerRequestDTO();
        raviRequest.setCustomerId("CUS-1002");
        raviRequest.setFullName("Ravi Singh");
        raviRequest.setEmail("ravi.singh@example.com");
        raviRequest.setStatus("PROSPECT");

        CustomerResponseDTO ravi = facade.create(raviRequest, correlationId);

        System.out.println("Created: "
                + ravi.getCustomerId()
                + " | "
                + ravi.getFullName()
                + " | Status: "
                + ravi.getStatus());


        System.out.println("\n=== Fetching customers as Response DTOs ===");

        CustomerResponseDTO fetchedAmina = facade.getById("CUS-1001", correlationId);
        CustomerResponseDTO fetchedRavi = facade.getById("CUS-1002", correlationId);

        printResponse(fetchedAmina);
        printResponse(fetchedRavi);

        System.out.println("\nCorrelation ID: " + correlationId);
        System.out.println("\nDemo completed successfully!");
    }

    private static void printResponse(CustomerResponseDTO customer) {
        System.out.println(
                "Response DTO: "
                        + customer.getCustomerId()
                        + " | "
                        + customer.getFullName()
                        + " | Status: "
                        + customer.getStatus()
        );
    }
}