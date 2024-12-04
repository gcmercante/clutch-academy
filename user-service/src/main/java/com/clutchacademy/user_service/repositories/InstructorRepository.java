package com.clutchacademy.user_service.repositories;

import com.clutchacademy.user_service.models.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InstructorRepository extends UserRepository, JpaRepository<Instructor, Integer> {
}
