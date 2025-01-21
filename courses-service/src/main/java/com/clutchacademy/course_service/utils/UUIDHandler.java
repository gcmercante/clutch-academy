package com.clutchacademy.course_service.utils;

import java.util.UUID;

public class UUIDHandler {
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
