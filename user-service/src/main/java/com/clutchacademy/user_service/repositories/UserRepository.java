package com.clutchacademy.user_service.repositories;

import com.clutchacademy.user_service.models.Student;

import java.util.Optional;

public interface UserRepository  {
    Optional<Student> findByEmail(String email);
}
