package com.northstar.crm.api;

import com.northstar.crm.dto.CustomerRequestDTO;
import com.northstar.crm.dto.CustomerResponseDTO;
import com.northstar.crm.dto.CustomerMapper;
import com.northstar.crm.service.CustomerService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerApiFacade {
    private final CustomerService service;
    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    public CustomerApiFacade(CustomerService service) {
        this.service = service;
    }

    public CustomerResponseDTO getById(String customerId, String correlationId) {
        var entity = service.getCustomer(customerId);
        return CustomerMapper.toResponse(entity);
    }

    public CustomerResponseDTO create(CustomerRequestDTO request, String correlationId) {
        Set<ConstraintViolation<CustomerRequestDTO>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            String detail = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining("; "));
            throw new IllegalArgumentException(
                    "validation failed [" + correlationId + "]: " + detail);
        }
        var customer = CustomerMapper.toEntity(request);

        var saved = service.createCustomer(
                customer.getCustomerId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getStatus()
        );


        return CustomerMapper.toResponse(saved);
    }
}