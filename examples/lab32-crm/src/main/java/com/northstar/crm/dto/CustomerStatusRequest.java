package com.northstar.crm.dto;

import jakarta.validation.constraints.NotBlank;

public class CustomerStatusRequest {
    @NotBlank
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
