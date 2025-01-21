package com.clutchacademy.course_service.application.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.clutchacademy.course_service.application.dtos.CreateCourseRequest;
import com.clutchacademy.course_service.application.dtos.CreateSectionRequest;
import com.clutchacademy.course_service.application.dtos.CreateSectionsRequest;
import com.clutchacademy.course_service.application.dtos.UpdateCourseRequest;
import com.clutchacademy.course_service.application.dtos.User;
import com.clutchacademy.course_service.domain.entity.Course;
import com.clutchacademy.course_service.domain.entity.Section;
import com.clutchacademy.course_service.domain.enums.Status;
import com.clutchacademy.course_service.domain.repositories.CourseRepository;
import com.clutchacademy.course_service.exceptions.NotFoundException;
import com.clutchacademy.course_service.utils.UUIDHandler;

@Service
public class CourseApplicationService {
    private final CourseRepository coursesRepository;
    private final UserApplicationService userService;

    public CourseApplicationService(
        CourseRepository coursesRepository,
        UserApplicationService userService
    ) {
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

    public Course addSections(CreateSectionsRequest request) {
        Optional<Course> courseToUpdate = coursesRepository.findById(request.getCourseId());

        if(courseToUpdate.isEmpty()) {
            throw new NotFoundException("Course not found");
        }

        Course course = courseToUpdate.get();
        List<Section> sections = mapToListOfSection(request.getSections());

        course.addSections(sections);

        return coursesRepository.save(course);
    }

    private List<Section> mapToListOfSection(List<CreateSectionRequest> sectionRequests) {
        return sectionRequests.stream()
            .map(section -> Section.builder()
                .id(UUIDHandler.generateUUID())
                .title(section.getTitle())
                .description(section.getDescription())
                .build()
            )
            .collect(Collectors.toList());
    }
}
