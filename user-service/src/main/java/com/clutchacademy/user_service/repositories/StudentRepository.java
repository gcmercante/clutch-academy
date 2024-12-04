package com.clutchacademy.user_service.repositories;

import com.clutchacademy.user_service.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends UserRepository, JpaRepository<Student, Integer> {
}
