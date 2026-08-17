package com.northstar.crm.soap;

import com.northstar.crm.model.Customer;
import com.northstar.crm.service.CustomerService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.Element;

@Endpoint
public class CustomerEndpoint {
    private final CustomerService customerService;
    private final CustomerSoapMapper mapper;

    public CustomerEndpoint(CustomerService customerService, CustomerSoapMapper mapper) {
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @PayloadRoot(namespace = CustomerSoapMapper.NAMESPACE_URI, localPart = "GetCustomerRequest")
    @ResponsePayload
    public Element getCustomer(@RequestPayload Element request) {
        String customerId = mapper.customerIdFromGetRequest(request);
        Customer customer = customerService.get(customerId);
        return mapper.toGetCustomerResponse(customer);
    }
}
