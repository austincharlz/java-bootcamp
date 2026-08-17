package com.northstar.crm.controller;

import com.northstar.crm.api.ApiResult;
import com.northstar.crm.api.CustomerApiFacade;
import com.northstar.crm.dto.CustomerRequestDTO;

public class CustomerController {

    private final CustomerApiFacade customerFacade;

    public CustomerController(CustomerApiFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    public ApiResult create(CustomerRequestDTO request) {
        return customerFacade.create(request, "lab-request-001");
    }

    public ApiResult getById(String customerId) {
        return customerFacade.getById(customerId, "lab-request-001");
    }
}