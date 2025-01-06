package com.clutchacademy.course_service.domain.dtos;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class CreateCourseRequest {
    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Instructor ID is mandatory")
    private String instructorId;
}
