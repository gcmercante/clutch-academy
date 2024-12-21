package com.clutchacademy.user_service.domain.dtos;

import lombok.Data;

@Data
public class UpdateUser {
    private String firstName;
    private String lastName;
    private String email;
}
