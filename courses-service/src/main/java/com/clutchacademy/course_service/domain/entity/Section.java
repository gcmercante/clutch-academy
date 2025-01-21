package com.clutchacademy.course_service.domain.entity;

import java.util.List;
import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Section {
    private String id;
    private String title;
    private String description;
    private List<Lesson> lessons;

    public void addLesson(Lesson lesson) {
        if (this.lessons == null) {
            this.lessons = new ArrayList<>();
        }
        this.lessons.add(lesson);
    }

    public void removeLesson(Lesson lesson) {
        if (this.lessons != null) {
            this.lessons.remove(lesson);
        }
    }

    public Lesson findLessonById(String lessonId) {
        if (this.lessons != null) {
            for (Lesson lesson : this.lessons) {
                if (lesson.getId().equals(lessonId)) {
                    return lesson;
                }
            }
        }
        return null;
    }
}
