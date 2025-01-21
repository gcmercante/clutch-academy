package com.clutchacademy.course_service.application.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSectionsRequest {
    private String courseId;
    private List<CreateSectionRequest> sections;
}
