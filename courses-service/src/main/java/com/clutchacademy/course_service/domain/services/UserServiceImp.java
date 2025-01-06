package com.clutchacademy.course_service.domain.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.clutchacademy.course_service.domain.dtos.User;
import com.clutchacademy.course_service.domain.dtos.UserServiceFailedResponse;
import com.clutchacademy.course_service.domain.dtos.UserServiceSuccessResponse;
import com.clutchacademy.course_service.exceptions.NotFoundException;

@Service
public class UserServiceImp implements UserService {
    private final RestTemplate restTemplate;
    
    @Value("${user-service.url}")
    private String userServiceUrl;

    public UserServiceImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public User getUserById(String id) {
        final String url = userServiceUrl + "/user/" + id;
        try {

            ResponseEntity<UserServiceSuccessResponse> responseEntity = restTemplate.getForEntity(url, UserServiceSuccessResponse.class);
            UserServiceSuccessResponse response = responseEntity.getBody();

            System.out.println("response: " + response);
    
            if(response == null || response.getData() == null || response.getData().getResource() == null) {
                throw new RuntimeException("Error getting instructor");
            }
            
            return response.getData().getResource();
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                UserServiceFailedResponse errorResponse = restTemplate.getForObject(url, UserServiceFailedResponse.class);
                final String message = errorResponse != null ? errorResponse.getMessage() : "Instructor not found";
                
                throw new NotFoundException(message);
            } else {
                throw new RuntimeException("Error getting instructor");
            }
        }
    }
}
