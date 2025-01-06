package com.clutchacademy.course_service.domain.services;

import com.clutchacademy.course_service.domain.dtos.User;

public interface UserService {
    User getUserById(String id);
}
