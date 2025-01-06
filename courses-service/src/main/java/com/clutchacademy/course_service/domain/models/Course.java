package com.clutchacademy.course_service.domain.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.clutchacademy.course_service.domain.dtos.User;
import com.clutchacademy.course_service.domain.enums.Status;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "courses")
public class Course {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String title;
    private String description;
    private User owner;
    private List<Section> sections;
    private Status status;

    public static Course builderFrom(Course course) {
        return Course.builder()
            .id(course.getId())
            .title(course.getTitle())
            .description(course.getDescription())
            .owner(course.getOwner())
            .sections(course.getSections())
            .status(course.getStatus())
            .build();
    }
}
