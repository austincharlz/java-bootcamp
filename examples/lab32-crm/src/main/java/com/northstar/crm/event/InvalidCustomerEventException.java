package com.northstar.crm.event;

public class InvalidCustomerEventException extends RuntimeException {
    public InvalidCustomerEventException(String message) {
        super(message);
    }
}
