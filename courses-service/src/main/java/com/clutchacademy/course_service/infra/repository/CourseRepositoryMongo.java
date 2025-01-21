package com.clutchacademy.course_service.infra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.clutchacademy.course_service.domain.entity.Course;
import com.clutchacademy.course_service.domain.repositories.CourseRepository;

@Repository
public interface CourseRepositoryMongo extends CourseRepository, MongoRepository<Course, String> {}
