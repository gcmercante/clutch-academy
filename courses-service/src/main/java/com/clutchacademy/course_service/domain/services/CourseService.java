package com.clutchacademy.course_service.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.clutchacademy.course_service.domain.dtos.CreateCourseRequest;
import com.clutchacademy.course_service.domain.dtos.UpdateCourseRequest;
import com.clutchacademy.course_service.domain.dtos.User;
import com.clutchacademy.course_service.domain.enums.Status;
import com.clutchacademy.course_service.domain.models.Course;
import com.clutchacademy.course_service.domain.repositories.CoursesRepository;
import com.clutchacademy.course_service.exceptions.NotFoundException;

@Service
public class CourseService {
    private final CoursesRepository coursesRepository;
    private final UserService userService;

    public CourseService(CoursesRepository coursesRepository, UserService userService) {
        this.coursesRepository = coursesRepository;
        this.userService = userService;
    }

    public Course create(CreateCourseRequest request) {
        User user = userService.getUserById(request.getInstructorId());

        Course course = Course.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .owner(user)
            .status(Status.ACTIVE)
            .sections(List.of())
            .build();

        Course savedCourse = coursesRepository.save(course);
        savedCourse.setId(savedCourse.getId().toString());

        return savedCourse;
    }

    public Course update(String courseId, UpdateCourseRequest request) {
        Optional<Course> courseToUpdate = coursesRepository.findById(courseId);

        if(courseToUpdate.isEmpty()) {
            throw new NotFoundException("Course not found");
        }

        Course course = courseToUpdate.get();

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());

        return coursesRepository.save(course);
    }

    public Course disable(String courseId) {
        Optional<Course> courseToUpdate = coursesRepository.findById(courseId);

        if(courseToUpdate.isEmpty()) {
            throw new NotFoundException("Course not found");
        }

        Course course = courseToUpdate.get();
        course.setStatus(Status.INACTIVE);

        return coursesRepository.save(course);
    }

    public Course enable(String courseId) {
        Optional<Course> courseToUpdate = coursesRepository.findById(courseId);

        if(courseToUpdate.isEmpty()) {
            throw new NotFoundException("Course not found");
        }

        Course course = courseToUpdate.get();
        course.setStatus(Status.ACTIVE);

        return coursesRepository.save(course);
    }

    public Course findById(String courseId) {
        Optional<Course> course = coursesRepository.findById(courseId);

        if(course.isEmpty()) {
            throw new NotFoundException("Course not found");
        }

        return course.get();
    }

    public List<Course> find() {
        return coursesRepository.findAll();
    }
}
