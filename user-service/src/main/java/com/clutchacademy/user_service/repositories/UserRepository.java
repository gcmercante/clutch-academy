package com.clutchacademy.user_service.repositories;

import com.clutchacademy.user_service.models.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByEmail(String email);
}
