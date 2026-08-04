package com.northstar.crm.api;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customers;

    public CustomerController(CustomerService customers) {
        this.customers = customers;
    }

    @PostMapping
    public ResponseEntity<Customer> addCustomer(
            @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId,
            @RequestBody Customer body) {
        String corr = correlationId != null ? correlationId : "lab-request-001";
        var created = customers.addCustomer(body, corr);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("X-Correlation-Id", corr)
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> get(
            @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId,
            @PathVariable String id) {
        String corr = correlationId != null ? correlationId : "lab-request-001";
        return customers.findById(id)
                .map(c -> ResponseEntity.ok().header("X-Correlation-Id", corr).body(c))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Correlation-Id", corr).build());
    }
}