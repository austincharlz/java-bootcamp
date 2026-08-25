package com.northstar.crm.api;

import com.northstar.crm.customer.DuplicateCustomerException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ProblemDetail> duplicate(DuplicateCustomerException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Duplicate customer");
        return withCorrelation(ResponseEntity.status(HttpStatus.CONFLICT), problem);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> conflict(DataIntegrityViolationException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Customer already exists");
        problem.setTitle("Conflict");
        return withCorrelation(ResponseEntity.status(HttpStatus.CONFLICT), problem);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ProblemDetail> optimistic(ObjectOptimisticLockingFailureException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "The record was updated by another request.");
        problem.setTitle("Optimistic lock conflict");
        return withCorrelation(ResponseEntity.status(HttpStatus.CONFLICT), problem);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> invalidRequest(IllegalArgumentException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Invalid request");
        return withCorrelation(ResponseEntity.badRequest(), problem);
    }

    private ResponseEntity<ProblemDetail> withCorrelation(ResponseEntity.BodyBuilder builder, ProblemDetail problem) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Correlation-Id", "lab-request-001");
        return builder.headers(headers).body(problem);
    }
}