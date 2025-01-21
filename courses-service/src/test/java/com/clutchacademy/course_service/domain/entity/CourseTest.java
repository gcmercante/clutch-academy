package com.clutchacademy.course_service.domain.entity;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.clutchacademy.course_service.application.dtos.CreateCourseRequest;
import com.clutchacademy.course_service.application.services.CourseApplicationService;
import com.clutchacademy.course_service.application.services.UserApplicationService;
import com.clutchacademy.course_service.utils.CourseRequestMock;

public class CourseTest {
    CreateCourseRequest createCourseRequest = CourseRequestMock.getMockCreateCourseRequest();


    @Mock
    UserApplicationService userService;

    @InjectMocks
    private CourseApplicationService courseService;

    @Test
    void shouldCreateCourseAndAddSection() {
        Course course = courseService.create(createCourseRequest);
        
        courseService.addSection(course.getId(), "Section 1");
    }
}
