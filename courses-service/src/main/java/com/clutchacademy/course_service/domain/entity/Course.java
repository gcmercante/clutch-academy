package com.clutchacademy.course_service.domain.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.clutchacademy.course_service.application.dtos.User;
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

    public void addSections(List<Section> sections) {
        if (this.sections == null) {
            this.sections = new ArrayList<Section>();
        }
        
        for (Section section : sections) {
            this.sections.add(section);
        }

        // this.sections.addAll(sections);
    }

    public void removeSection(Section section) {
        if (this.sections != null) {
            this.sections.remove(section);
        }
    }

    public Section findSectionById(String sectionId) {
        if (this.sections != null) {
            for (Section section : this.sections) {
                if (section.getId().equals(sectionId)) {
                    return section;
                }
            }
        }
        return null;
    }

    public void updateStatus(Status newStatus) {
        this.status = newStatus;
    }
}