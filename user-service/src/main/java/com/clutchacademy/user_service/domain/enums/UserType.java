package com.clutchacademy.user_service.domain.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserType {
    ADMIN,
    STUDENT,
    INSTRUCTOR,
    @JsonEnumDefaultValue
    UNKNOWN;
}
