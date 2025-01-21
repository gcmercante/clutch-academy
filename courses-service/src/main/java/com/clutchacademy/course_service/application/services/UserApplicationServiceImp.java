package com.clutchacademy.course_service.application.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.clutchacademy.course_service.application.dtos.User;
import com.clutchacademy.course_service.application.dtos.UserServiceFailedResponse;
import com.clutchacademy.course_service.application.dtos.UserServiceSuccessResponse;
import com.clutchacademy.course_service.exceptions.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserApplicationServiceImp implements UserApplicationService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    @Value("${user-service.url}")
    private String userServiceUrl;

    public UserApplicationServiceImp(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public User getUserById(String id) {
        final String url = userServiceUrl + "/user/" + id;
        try {
            ResponseEntity<UserServiceSuccessResponse> responseEntity = restTemplate.getForEntity(url, UserServiceSuccessResponse.class);
            UserServiceSuccessResponse response = responseEntity.getBody();
    
            if(response == null || response.getData() == null || response.getData().getResource() == null) {
                throw new ServiceException("Error getting instructor");
            }
            
            return response.getData().getResource();
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                UserServiceFailedResponse errorResponse = null;

                try {
                    errorResponse = objectMapper.readValue(e.getResponseBodyAsString(), UserServiceFailedResponse.class);
                } catch (Exception ex) {
                    throw new RuntimeException("Error parsing error response", ex);
                }

                final String message = errorResponse != null ? errorResponse.getMessage() : "User not found";
                throw new ServiceException("Error getting user: " + message);
            } else {
                throw new ServiceException("Error getting user: " + e.getMessage());
            }
        }
    }
}
