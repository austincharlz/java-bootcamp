package com.northstar.crm.customer;

import java.util.Locale;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerEntity toEntity(CreateCustomerRequest request, String normalizedEmail) {
        CustomerEntity entity = new CustomerEntity();
        entity.setPublicId(request.publicId() != null && !request.publicId().isBlank()
                ? request.publicId()
                : UUID.randomUUID().toString());
        entity.setFullName(request.fullName());
        entity.setEmail(normalizedEmail);
        entity.setStatus(request.status() == null || request.status().isBlank()
                ? "ACTIVE"
                : request.status().trim().toUpperCase(Locale.ROOT));
        return entity;
    }

    public CustomerResponse toResponse(CustomerEntity entity) {
        return new CustomerResponse(
                entity.getCustomerId(),
                entity.getPublicId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getStatus(),
                entity.getVersion(),
                entity.getCreatedAt()
        );
    }
}
