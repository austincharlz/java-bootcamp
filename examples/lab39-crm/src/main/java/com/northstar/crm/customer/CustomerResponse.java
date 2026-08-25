package com.northstar.crm.customer;

import java.time.Instant;

public record CustomerResponse(
        Long customerId,
        String publicId,
        String fullName,
        String email,
        String status,
        Long version,
        Instant createdAt
) {
}
