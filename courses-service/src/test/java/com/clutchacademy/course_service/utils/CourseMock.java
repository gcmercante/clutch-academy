package com.clutchacademy.course_service.utils;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;

import com.clutchacademy.course_service.domain.dtos.User;
import com.clutchacademy.course_service.domain.enums.Status;
import com.clutchacademy.course_service.domain.models.Course;
import com.clutchacademy.course_service.domain.models.Lesson;
import com.clutchacademy.course_service.domain.models.Resource;
import com.clutchacademy.course_service.domain.models.Section;


public class CourseMock {
    public static Course getMockCourse() {
        User instructor = InstructorMock.getMockInstructor();

        Resource resource1 = getMockResource("resource1", "Resource 1", "Description for resource 1", "http://example.com/resource1");

        Resource resource2 = getMockResource("resource2", "Resource 2", "Description for resource 2", "http://example.com/resource2");

        Lesson lesson1 = getMockLesson("lesson1", "Introduction to eSports", "Introduction to eSports", "http://example.com/video1", Arrays.asList(resource1));

        Lesson lesson2 = getMockLesson("lesson2", "Advanced eSports", "Advanced concepts in eSports", "http://example.com/video2", Arrays.asList(resource2));

        Section section1 = getMockSection("section1", "Introduction", "Introduction section", Arrays.asList(lesson1));

        Section section2 = getMockSection("section2", "Advanced", "Advanced section", Arrays.asList(lesson2));
        
        return Course.builder()
            .id(new ObjectId().toString())
            .title("eSports Mastery")
            .description("Comprehensive course on eSports")
            .owner(instructor)
            .sections(Arrays.asList(section1, section2))
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

    private static Section getMockSection(String id, String title, String description, List<Lesson> lessons) {
        return Section.builder()
            .id(id)
            .title(title)
            .description(description)
            .lessons(lessons)
            .build();
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
