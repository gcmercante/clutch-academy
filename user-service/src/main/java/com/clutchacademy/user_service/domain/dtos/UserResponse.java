package com.clutchacademy.user_service.domain.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private Boolean active;
    private String email;
}
