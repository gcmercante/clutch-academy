package com.clutchacademy.course_service.exceptionhandlers;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.persistence.EntityExistsException;

import com.clutchacademy.course_service.application.dtos.ConstraintViolation;
import com.clutchacademy.course_service.application.dtos.FailResponse;
import com.clutchacademy.course_service.exceptions.NotFoundException;
import com.clutchacademy.course_service.exceptions.ServiceException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice("com.clutchacademy")
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailResponse<ConstraintViolation>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        AtomicBoolean isMissingProp = new AtomicBoolean(false);
        List<ConstraintViolation> errors = exception.getFieldErrors()
                .stream()
                .map(violation -> {
                    if (Objects.requireNonNull(violation.getDefaultMessage()).endsWith("must be defined")) {
                        isMissingProp.set(true);
                    }

                    return ConstraintViolation.builder()
                            .message(violation.getDefaultMessage())
                            .fieldName(violation.getField())
                            .rejectedValue(Objects.isNull(violation.getRejectedValue())
                                    ? null
                                    : Objects.toString(violation.getRejectedValue(), null)
                            ).build();
                })
                .toList();

        FailResponse<ConstraintViolation> errorResponse = FailResponse.<ConstraintViolation>builder()
                .status("error")
                .message("Validation error")
                .errors(errors)
                .build();

        HttpStatus status = isMissingProp.get() ? BAD_REQUEST : UNPROCESSABLE_ENTITY;
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<FailResponse<?>> handleHttpNotFoundException(NotFoundException exception) {
        FailResponse<?> response = FailResponse.builder()
                .status("error")
                .message("Resource not found")
                .build();

        return ResponseEntity.status(NOT_FOUND).body(response);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<FailResponse<?>> handleServiceException(ServiceException exception) {
        FailResponse<?> response = FailResponse.builder()
                .status("error")
                .message("Internal Server Error")
                .build();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<FailResponse<?>> handleEntityExistsException(EntityExistsException exception) {
        FailResponse<?> response = FailResponse.builder()
                .status("error")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailResponse<?>> handleAnyException(Exception exception) {
        FailResponse<?> response = FailResponse.builder()
                .status("error")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(response);
    }
}
