package com.clutchacademy.user_service.repositories;

import com.clutchacademy.user_service.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends UserRepository, JpaRepository<Student, Integer> {
}
