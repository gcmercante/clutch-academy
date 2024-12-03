package com.clutchacademy.user_service.exceptionhandler;

import com.clutchacademy.user_service.dtos.ConstraintViolation;
import com.clutchacademy.user_service.exceptions.HttpNotFoundException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice("com.clutchacademy")
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ProblemDetail validationProblemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Validation error");

        List<ConstraintViolation> errors = exception.getFieldErrors()
                .stream()
                .map(violation -> ConstraintViolation.builder()
                        .message(violation.getDefaultMessage())
                        .fieldName(violation.getField())
                        .rejectedValue(Objects.isNull(violation.getRejectedValue())
                                ? null
                                : violation.getRejectedValue().toString()
                        ).build())
                .toList();
        validationProblemDetail.setProperty("errors", errors);

        return validationProblemDetail;
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ProblemDetail handleUnrecognizedPropertyException(UnrecognizedPropertyException exception) {
        return ProblemDetail.forStatusAndDetail(
                BAD_REQUEST,
                "The property " + exception.getPropertyName() + " is not allowed"
        );
    }

    @ExceptionHandler(HttpNotFoundException.class)
    public ProblemDetail handleHttpNotFoundException(HttpNotFoundException exception) {
        return exception.toProblemDetail();
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleAnyException(Exception exception) {
        return ErrorResponse.builder(exception, INTERNAL_SERVER_ERROR, exception.getMessage()).build();
    }
}
