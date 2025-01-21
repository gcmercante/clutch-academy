package com.clutchacademy.course_service.application.services;

import com.clutchacademy.course_service.application.dtos.User;

public interface UserApplicationService {
    User getUserById(String id);
}
