package com.clutchacademy.course_service.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import com.clutchacademy.course_service.application.dtos.CreateSectionsRequest;
import com.clutchacademy.course_service.application.dtos.User;
import com.clutchacademy.course_service.domain.entity.Course;
import com.clutchacademy.course_service.domain.entity.Lesson;
import com.clutchacademy.course_service.domain.entity.Resource;
import com.clutchacademy.course_service.domain.entity.Section;
import com.clutchacademy.course_service.domain.enums.Status;


public class CourseMock {
    public static Course getMockCourse() {
        User instructor = InstructorMock.getMockInstructor();

        return Course.builder()
            .id(new ObjectId().toString())
            .title("eSports Mastery")
            .description("Comprehensive course on eSports")
            .owner(instructor)
            .status(Status.ACTIVE)
            .build();
    }

    public static Course getMockCourseToInsert() {
        User instructor = InstructorMock.getMockInstructor();

        return Course.builder()
            .title("eSports Mastery")
            .description("Comprehensive course on eSports")
            .owner(instructor)
            .status(Status.ACTIVE)
            .build();
    }

    public static List<Section> getMockSection(CreateSectionsRequest sectionRequest) {
        return sectionRequest.getSections().stream()
            .map(section -> Section.builder()
                .id(UUIDHandler.generateUUID())
                .title(section.getTitle())
                .description(section.getDescription())
                .build()
            )
            .collect(Collectors.toList());
    }

    private static Lesson getMockLesson(String id, String title, String description, String videoUrl, List<Resource> resources) {
        return Lesson.builder()
            .id(id)
            .title(title)
            .description(description)
            .videoUrl(videoUrl)
            .resources(resources)
            .build();
    }

    private static Resource getMockResource(String id, String name, String description, String url) {
        return Resource.builder()
            .id(id)
            .name(name)
            .description(description)
            .url(url)
            .build();
    }
}
