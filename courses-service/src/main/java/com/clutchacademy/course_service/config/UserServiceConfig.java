package com.clutchacademy.course_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "user-service")
public class UserServiceConfig {
    private String url;
    private int timeout;
}
