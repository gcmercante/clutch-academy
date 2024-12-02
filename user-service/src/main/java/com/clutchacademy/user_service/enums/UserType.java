package com.clutchacademy.user_service.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserType {
    ADMIN,
    STUDENT,
    INSTRUCTOR,
    @JsonEnumDefaultValue
    UNKNOWN;

    public static UserType fromString(String value) {
        try {
            return UserType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
