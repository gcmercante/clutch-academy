package com.clutchacademy.user_service.utils;

import com.clutchacademy.user_service.domain.dtos.UpdateUser;
import com.clutchacademy.user_service.domain.dtos.UserRequest;
import com.clutchacademy.user_service.domain.enums.UserType;
import com.clutchacademy.user_service.domain.models.User;

public class MockUser {
    private MockUser() {}

    public enum MockType {
        PAYLOAD,
        USER_TYPE_NULL,
        UNSUPPORTED_USER_TYPE
    }

    public enum InvalidMockType {
        VALIDATION_ERROR,
        EMPTY_BODY
    }

    public static UserRequest getMockUserRequest(MockType mockType, UserType userType) {
        UserRequest mock = new UserRequest();

        switch (mockType) {
            case PAYLOAD -> {
                mock.setFirstName("John");
                mock.setLastName("Doe");
                mock.setUserType(userType);
                mock.setEmail("test@mail.com");
            }
            case USER_TYPE_NULL -> {
                mock.setFirstName("John");
                mock.setLastName("Doe");
                mock.setEmail("test@mail.com");
            }
            case UNSUPPORTED_USER_TYPE -> {
                mock.setFirstName("John");
                mock.setLastName("Doe");
                mock.setEmail("test@mail.com");
                mock.setUserType(UserType.valueOf("UNKNOWN"));
            }
        }

        return mock;
    }

    public static UserRequest getMockInvalidUserRequest(InvalidMockType type) {
        UserRequest mock = new UserRequest();

        switch (type) {
            case VALIDATION_ERROR -> {
                mock.setFirstName("John");
                mock.setLastName("Doe");
                mock.setEmail("invalid-email");
                mock.setUserType(UserType.STUDENT);
            }
            case EMPTY_BODY -> {
                mock.setFirstName("");
                mock.setLastName("Doe");
                mock.setEmail("");
                mock.setUserType(UserType.STUDENT);
            }
        }

        return mock;
    }

    public static User getMockUser(UserType userType) {
        User mock = new User();
        String prefix = userType == UserType.STUDENT ? "STU-" : "INS-";

        mock.setUserId(prefix + "123456");
        mock.setFirstName("John");
        mock.setLastName("Doe");
        mock.setActive(true);
        mock.setEmail("test@mail.com");
        mock.setType(userType);

        return mock;
    }

    public static UpdateUser getMockUpdateUser() {
        UpdateUser mock = new UpdateUser();

        mock.setFirstName("Jane");
        mock.setLastName("Smith");
        mock.setEmail("test@mail.com");

        return mock;
    }
}
