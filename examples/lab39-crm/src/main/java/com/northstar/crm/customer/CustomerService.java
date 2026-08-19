package com.northstar.crm.customer;

import java.util.Locale;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("fullName", "createdAt", "customerId", "status");

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerService(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {
        String email = normalize(request.email());
        if (repository.existsByEmail(email)) {
            throw new DuplicateCustomerException("Customer already exists for email=" + email);
        }
        CustomerEntity saved = repository.save(mapper.toEntity(request, email));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<CustomerResponse> pageByStatus(String status, int page, int size, String sortBy) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 100);
        String safeSort = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "fullName";

        Pageable pageable = PageRequest.of(
                safePage,
                safeSize,
                Sort.by(safeSort).ascending().and(Sort.by("customerId").ascending())
        );

        return repository.findByStatus(status, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<CustomerResponse> pageByStatus(String status, Pageable pageable) {
        return repository.findByStatus(status, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<CustomerEntity> pageByStatus(String status, int page, int size) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 100);
        Pageable pageable = PageRequest.of(
                safePage,
                safeSize,
                Sort.by("fullName").ascending().and(Sort.by("customerId").ascending())
        );
        return repository.findByStatus(status, pageable);
    }

    private String normalize(String email) {
        if (email == null) {
            return "";
        }
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
