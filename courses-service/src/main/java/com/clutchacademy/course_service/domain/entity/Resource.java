package com.clutchacademy.course_service.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Resource {
    private String id;
    private String name;
    private String description;
    private String url;
}
