package com.clutchacademy.user_service.dtos;

import com.clutchacademy.user_service.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class UserRequest {
    @NotBlank(message = "First name must be defined")
    private String firstName;
    @NotBlank(message = "Last name must be defined")
    private String lastName;
    @NotNull(message = "User type must be defined")
    private UserType userType;
    @NotEmpty(message = "Email must be defined")
    @Email(message = "The email provided is not valid. Please provide a valid email address (e.g., user@example.com).")
    private String email;
}
