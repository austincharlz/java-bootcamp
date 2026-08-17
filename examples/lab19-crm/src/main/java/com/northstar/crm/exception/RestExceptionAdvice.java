package com.northstar.crm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionAdvice {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex) {
        ErrorResponse resp = handler.fromBusiness(ex);
        return ResponseEntity.status(ex.getStatusHint())
                .header("X-Correlation-Id", ex.getCorrelationId())
                .body(resp);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex, HttpServletRequest request) {
        String corr = request != null && request.getHeader("X-Correlation-Id") != null ?
                request.getHeader("X-Correlation-Id") : "lab-request-001";
        ErrorResponse err = new ErrorResponse(400, "VALIDATION_FAILED", ex.getMessage(), corr, Map.of());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("X-Correlation-Id", corr).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex, HttpServletRequest request) {
        String corr = request != null && request.getHeader("X-Correlation-Id") != null ?
                request.getHeader("X-Correlation-Id") : "lab-request-001";
        ErrorResponse err = handler.fromUnexpected(ex, corr);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("X-Correlation-Id", corr).body(err);
    }
}
