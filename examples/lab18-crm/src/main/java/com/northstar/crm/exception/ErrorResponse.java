package com.northstar.crm.exception;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

public class ErrorResponse {

    private final Instant timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String correlationId;
    private final Map<String, String> errors;

    public ErrorResponse(
            int status,
            String error,
            String message,
            String correlationId,
            Map<String, String> errors) {

        this.timestamp = Instant.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.correlationId = correlationId;

        // Always keep errors present, even when empty
        this.errors = errors == null
                ? new LinkedHashMap<>()
                : new LinkedHashMap<>(errors);
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String toJson() {
        StringBuilder json = new StringBuilder();

        json.append("{")
                .append("\"timestamp\":\"")
                .append(timestamp)
                .append("\",")

                .append("\"status\":")
                .append(status)
                .append(",")

                .append("\"error\":\"")
                .append(error)
                .append("\",")

                .append("\"message\":\"")
                .append(message)
                .append("\",")

                .append("\"correlationId\":\"")
                .append(correlationId)
                .append("\",")

                .append("\"errors\":{");

        boolean first = true;

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            if (!first) {
                json.append(",");
            }

            json.append("\"")
                    .append(entry.getKey())
                    .append("\":\"")
                    .append(entry.getValue())
                    .append("\"");

            first = false;
        }

        json.append("}}");

        return json.toString();
    }
}