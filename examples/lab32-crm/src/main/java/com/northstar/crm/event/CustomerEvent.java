package com.northstar.crm.event;

import java.time.Instant;
import java.util.Objects;

/** Immutable CRM customer domain event. */
public record CustomerEvent(
        String eventId,
        String eventType,
        int eventVersion,
        Instant occurredAt,
        String customerId,
        String correlationId,
        String source,
        CustomerData data
) {
    public record CustomerData(String fullName, String status) {}

    public CustomerEvent {
        Objects.requireNonNull(eventId);
        Objects.requireNonNull(customerId);
        if (eventId.isBlank()) {
            throw new InvalidCustomerEventException("eventId is required");
        }
        if (customerId.isBlank()) {
            throw new InvalidCustomerEventException("customerId is required");
        }
        if (eventVersion != 1) {
            throw new UnsupportedEventVersionException("Unsupported eventVersion: " + eventVersion);
        }
    }
}