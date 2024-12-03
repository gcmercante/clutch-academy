package com.clutchacademy.user_service.exceptions;

import org.springframework.http.ProblemDetail;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class HttpNotFoundException extends RuntimeException {
    public HttpNotFoundException(String message) {
        super(message);
    }

    public ProblemDetail toProblemDetail() {
        return ProblemDetail.forStatusAndDetail(NOT_FOUND, this.getMessage());
    }
}
