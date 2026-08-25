package com.northstar.crm.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequest(
        @NotBlank(message = "fullName is required")
        @Size(max = 200, message = "fullName must be 200 chars or fewer")
        String fullName,

        @NotBlank(message = "email is required")
        @Email(message = "email must be valid")
        String email,

        @NotBlank(message = "status is required")
        String status,

        String publicId
) {
}
