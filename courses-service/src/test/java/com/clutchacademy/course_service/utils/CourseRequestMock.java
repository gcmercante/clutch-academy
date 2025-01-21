package com.clutchacademy.course_service.utils;

import java.util.List;

import com.clutchacademy.course_service.application.dtos.CreateCourseRequest;
import com.clutchacademy.course_service.application.dtos.CreateSectionRequest;
import com.clutchacademy.course_service.application.dtos.CreateSectionsRequest;
import com.clutchacademy.course_service.application.dtos.UpdateCourseRequest;

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

    public static CreateSectionsRequest getMockCreateSectionRequest() {
        return CreateSectionsRequest.builder()
            .sections(List.of(
                CreateSectionRequest.builder()
                    .title("Section 1")
                    .description("Section 1 description")
                    .build()
            ))
            .build();
    }
}
