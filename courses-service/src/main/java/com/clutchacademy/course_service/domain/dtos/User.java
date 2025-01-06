package com.clutchacademy.course_service.domain.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean active; 
}
