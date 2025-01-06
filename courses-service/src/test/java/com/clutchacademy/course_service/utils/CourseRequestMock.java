package com.clutchacademy.course_service.utils;

import com.clutchacademy.course_service.domain.dtos.CreateCourseRequest;
import com.clutchacademy.course_service.domain.dtos.UpdateCourseRequest;

public class CourseRequestMock {
    public static CreateCourseRequest getMockCreateCourseRequest() {
        return CreateCourseRequest.builder()
            .title("eSports Mastery")
            .description("Comprehensive course on eSports")
            .instructorId("instructor1")
            .build();
    }

    public static UpdateCourseRequest getMockUpdateCourseRequest() {
        return UpdateCourseRequest.builder()
                .title("Mock Title")
                .description("Mock Description")
                .build();
    }
}
