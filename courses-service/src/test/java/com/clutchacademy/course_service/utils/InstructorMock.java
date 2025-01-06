package com.clutchacademy.course_service.utils;

import com.clutchacademy.course_service.domain.dtos.User;

public class InstructorMock {
    public static User getMockInstructor() {
        User instructor = User.builder()
            .userId("instructor1")
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@example.com")
            .build();
        
        return instructor;
    }
}