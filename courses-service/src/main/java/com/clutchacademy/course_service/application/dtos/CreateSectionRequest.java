package com.clutchacademy.course_service.application.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateSectionRequest {
    private String title;
    private String description;
}
