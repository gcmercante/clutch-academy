package com.clutchacademy.course_service.domain.models;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Section {
    private String id;
    private String title;
    private String description;
    private List<Lesson> lessons;
}
