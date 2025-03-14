package com.clutchacademy.course_service.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConstraintViolation {
    private String fieldName;
    private String message;
    private String rejectedValue;
}
