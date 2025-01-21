package com.clutchacademy.course_service.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.clutchacademy.course_service.domain.entity.Course;

public interface CourseRepository {
    Course save(Course course);
    Optional<Course> findById(String courseId);
    List<Course> findAll();
}
