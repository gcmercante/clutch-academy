package com.clutchacademy.course_service.domain.entity;

import java.util.List;
import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Lesson {
    private String id;
    private String title;
    private String description;
    private String videoUrl;
    private List<Resource> resources;

    public void addResource(Resource resource) {
        if (this.resources == null) {
            this.resources = new ArrayList<>();
        }
        this.resources.add(resource);
    }

    public void removeResource(Resource resource) {
        if (this.resources != null) {
            this.resources.remove(resource);
        }
    }

    public Resource findResourceById(String resourceId) {
        if (this.resources != null) {
            for (Resource resource : this.resources) {
                if (resource.getId().equals(resourceId)) {
                    return resource;
                }
            }
        }
        return null;
    }
}