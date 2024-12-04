package com.clutchacademy.user_service.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import com.clutchacademy.user_service.dtos.FailResponse;
import com.clutchacademy.user_service.dtos.SuccessResponse;
import com.clutchacademy.user_service.dtos.UpdateUser;
import com.clutchacademy.user_service.dtos.UserRequest;
import com.clutchacademy.user_service.dtos.UserResponse;
import com.clutchacademy.user_service.enums.UserType;
import com.clutchacademy.user_service.services.UserService;
import com.clutchacademy.user_service.utils.MockUser;
import com.clutchacademy.user_service.utils.MockUser.InvalidMockType;
import com.clutchacademy.user_service.utils.MockUser.MockType;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTests {
    private final UserRequest userRequest = MockUser.getMockUserRequest(MockType.PAYLOAD, UserType.STUDENT);
    private final UserRequest validationError = MockUser.getMockInvalidUserRequest(InvalidMockType.VALIDATION_ERROR);
    private final UserRequest emptyBody = MockUser.getMockInvalidUserRequest(InvalidMockType.EMPTY_BODY);
    private final UpdateUser updateUser = MockUser.getMockUpdateUser();

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DataSource dataSource;

    private static JdbcTemplate jdbcTemplate;

    @BeforeAll
    void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DELETE FROM students");
        jdbcTemplate.execute("DELETE FROM instructors");
    }

    @Nested
    class CreateTests {

        @Test
        void testCreateUserSuccess() {
            ResponseEntity<SuccessResponse> response = restTemplate.postForEntity(
                    createURLWithPort("/user"), userRequest, SuccessResponse.class);
    
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody().getStatus()).isEqualTo("success");
        }
    
        @Test
        void testCreateUserValidationError() {
            ResponseEntity<FailResponse> response = restTemplate.postForEntity(createURLWithPort("/user"), validationError, FailResponse.class);
    
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
            assertThat(response.getBody().getStatus()).isEqualTo("error");
        }

        @Test
        void testCreateUserEmptyBody() {
            ResponseEntity<FailResponse> response = restTemplate.postForEntity(createURLWithPort("/user"), emptyBody, FailResponse.class);
    
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody().getStatus()).isEqualTo("error");
        }

        @Test
        void testCreateUserNullBody() {
            ResponseEntity<FailResponse> response = restTemplate.postForEntity(createURLWithPort("/user"), null, FailResponse.class);
    
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
            assertThat(response.getBody().getStatus()).isEqualTo("error");
        }

        @Test
        void testCreateUserWithSameEmail() {
            userService.create(userRequest);
            ResponseEntity<FailResponse> response = restTemplate.postForEntity(createURLWithPort("/user"), userRequest, FailResponse.class);
    
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody().getStatus()).isEqualTo("error");
        }
    }

    @Nested
    class FindTests {
        @Test
        void testFindUsersSuccess() {
            userService.create(userRequest);
            ResponseEntity<SuccessResponse> response = restTemplate.getForEntity(createURLWithPort("/user"), SuccessResponse.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody().getStatus()).isEqualTo("success");
        }
    }

    @Nested
    class FindByIdTests {
        @Test
        void testFindUserSuccess() {
            UserResponse createdUser = userService.create(userRequest);

            ResponseEntity<SuccessResponse> response = restTemplate.getForEntity(
                createURLWithPort("/user/" + createdUser.getUserId()), SuccessResponse.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody().getStatus()).isEqualTo("success");
        }
        
        @Test
        void testFindUserByIdNotFound() {
            ResponseEntity<FailResponse> response = restTemplate.getForEntity(createURLWithPort("/user/invalid-id"), FailResponse.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(response.getBody().getStatus()).isEqualTo("error");
        }
    }

    @Nested
    class UpdateTests {
        @Test
        void testUpdateUserSuccess() {
            UserResponse createdUser = userService.create(userRequest);
            HttpEntity<UpdateUser> requestEntity = new HttpEntity<>(updateUser);

            ResponseEntity<SuccessResponse> response = restTemplate.exchange(
                createURLWithPort("/user/" + createdUser.getUserId()), HttpMethod.PUT, requestEntity, SuccessResponse.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody().getStatus()).isEqualTo("success");
        }

        @Test
        void testUpdateInvalidUser() {
            HttpEntity<UpdateUser> requestEntity = new HttpEntity<>(updateUser);

            ResponseEntity<FailResponse> response = restTemplate.exchange(
                createURLWithPort("/user/invalid-id"), HttpMethod.PUT, requestEntity, FailResponse.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(response.getBody().getStatus()).isEqualTo("error");
        }
    }

    @Nested
    class DisableTests {
        @Test
        void testDisableUserSuccess() {
            UserResponse createdUser = userService.create(userRequest);
            ResponseEntity<Void> response = restTemplate.exchange(createURLWithPort("/user/disable/" + createdUser.getUserId()), HttpMethod.PUT, null, Void.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }

        @Test
        void testDisableInvalidUser() {
            ResponseEntity<FailResponse> response = restTemplate.exchange(
                createURLWithPort("/user/disable/invalid-id"), HttpMethod.PUT, null, FailResponse.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(response.getBody().getStatus()).isEqualTo("error");
        }
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
