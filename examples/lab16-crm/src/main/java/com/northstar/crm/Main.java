package com.northstar.crm;

import com.northstar.crm.api.CustomerApiFacade;
import com.northstar.crm.api.ApiResult;
import com.northstar.crm.dto.CustomerRequestDTO;
import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import com.northstar.crm.exception.BusinessException;
import com.northstar.crm.exception.ErrorResponse;
import com.northstar.crm.exception.GlobalExceptionHandler;
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

        // API layer
        CustomerApiFacade facade = new CustomerApiFacade(service);

        String correlationId = "lab-request-001";

        // Step 6 — Demo not-found error for missing customer
        ApiResult missingCustomer =
                facade.getById("CUS-9999", correlationId);

        if (missingCustomer instanceof ApiResult.Fail fail) {
            System.out.println("Step 6 - Not Found:");
            System.out.println(fail.error().toJson());
        }

         // Step 7 — Demo business conflict on illegal transition
        Customer amina = new Customer(
                "CUS-1001",
                "Amina Khan",
                "amina@example.com",
                "555-1001",
                CustomerStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        service.addCustomer(amina);

        //  Attempt illegal transition:
        try {
            service.changeStatus(
                    "CUS-1001",
                    CustomerStatus.PROSPECT,
                    correlationId
            );

        } catch (BusinessException ex) {

            ErrorResponse error =
                    new GlobalExceptionHandler()
                            .fromBusiness(ex);

            System.out.println("\nStep 7 - Business Conflict:");
            System.out.println(error.toJson());
        }

        Customer currentAmina =
                service.findById("CUS-1001")
                        .orElseThrow();

        System.out.println(
                "\nCUS-1001 status after failed transition: "
                        + currentAmina.getStatus()
        );

        CustomerRequestDTO request = new CustomerRequestDTO();

        request.setCustomerId("CUS-1001");
        request.setFullName("");
        request.setEmail("bad-email");
        request.setStatus("ACTIVE");

        // Failure 2 — Demo multiple validation errors
        ApiResult validationResult =
                facade.create(request, correlationId);

        if (validationResult instanceof ApiResult.Fail fail) {
            System.out.println("\nFailure #2 - Validation Failure:");
            System.out.println(fail.error().toJson());
        }

        // Failure 3
        System.out.println("\nFailure #3 - Not-Found Twice:");
        ApiResult first =
                facade.getById(
                        "CUS-9999",
                        "request-one");

        ApiResult second =
                facade.getById(
                        "CUS-9999",
                        "request-two");

        if(first instanceof ApiResult.Fail fail)
            System.out.println(fail.error().toJson());

        if(second instanceof ApiResult.Fail fail)
            System.out.println(fail.error().toJson());
    }
}