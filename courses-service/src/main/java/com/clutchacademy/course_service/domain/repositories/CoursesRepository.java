package com.clutchacademy.course_service.domain.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.clutchacademy.course_service.domain.models.Course;

@Repository
public interface CoursesRepository extends MongoRepository<Course, String> {}
