package com.clutchacademy.course_service.application.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import com.clutchacademy.course_service.application.dtos.CreateCourseRequest;
import com.clutchacademy.course_service.application.dtos.UpdateCourseRequest;
import com.clutchacademy.course_service.application.services.CourseApplicationService;
import com.clutchacademy.course_service.domain.entity.Course;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseApplicationService courseService;

    public CourseController(CourseApplicationService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CreateCourseRequest request) {
        Course course = courseService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable String courseId, @Valid @RequestBody UpdateCourseRequest request) {
        Course course = courseService.update(courseId, request);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/disable/{courseId}")
    public ResponseEntity<Course> disableCourse(@PathVariable String courseId) {
        courseService.disable(courseId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/enable/{courseId}")
    public ResponseEntity<Course> enableCourse(@PathVariable String courseId) {
        courseService.enable(courseId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable String courseId) {
        Course course = courseService.findById(courseId);
        return ResponseEntity.ok(course);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.find();
        return ResponseEntity.ok(courses);
    }
}
