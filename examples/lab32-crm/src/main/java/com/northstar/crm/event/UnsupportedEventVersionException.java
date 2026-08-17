package com.northstar.crm.event;

public class UnsupportedEventVersionException extends RuntimeException {
    public UnsupportedEventVersionException(String message) {
        super(message);
    }
}
