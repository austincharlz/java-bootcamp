package com.northstar.crm.customer;

import jakarta.validation.Valid;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("fullName", "createdAt", "customerId", "status");

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public CustomerResponse create(@Valid @RequestBody CreateCustomerRequest request) {
        return service.create(request);
    }

    @GetMapping("/{publicId}")
    @PreAuthorize("@customerAccessPolicy.canReadCustomer(authentication, #publicId)")
    public CustomerResponse getByPublicId(@PathVariable String publicId) {
        return service.getByPublicId(publicId);
    }

    @GetMapping
    public Page<CustomerResponse> list(
            @RequestParam(defaultValue = "ACTIVE") String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "fullName") String sortBy) {

        if (page < 0) {
            throw new IllegalArgumentException("page must be >= 0");
        }

        int safeSize = Math.min(Math.max(size, 1), 100);
        String safeSort = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "fullName";

        Pageable pageable = PageRequest.of(
                page,
                safeSize,
                Sort.by(safeSort).ascending().and(Sort.by("customerId").ascending())
        );

        return service.pageByStatus(status, pageable);
    }
}