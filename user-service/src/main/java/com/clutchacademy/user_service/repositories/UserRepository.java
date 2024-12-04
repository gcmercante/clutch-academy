package com.clutchacademy.user_service.repositories;

import com.clutchacademy.user_service.models.Student;
import com.clutchacademy.user_service.models.User;

import java.util.Optional;

public interface UserRepository  {
    Optional<User> findByUserId(String userId);
    Optional<User> findByEmail(String email);
}
