package com.clutchacademy.course_service.domain.dtos;

import lombok.Data;

@Data
public class UserServiceSuccessResponse {
    private String status;
    private DataWrapper data;

    @Data
    public static class DataWrapper {
        private String id;
        private User resource;
    }
}