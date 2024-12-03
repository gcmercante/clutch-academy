package com.clutchacademy.user_service.utils;

import com.clutchacademy.user_service.dtos.UpdateUser;
import com.clutchacademy.user_service.dtos.UserRequest;
import com.clutchacademy.user_service.enums.UserType;
import com.clutchacademy.user_service.models.Instructor;
import com.clutchacademy.user_service.models.Student;

public class MockUser {
    private MockUser() {}

    public enum MockType {
        PAYLOAD,
        FROM_DB,
        USER_TYPE_NULL,
        UNSUPPORTED_USER_TYPE
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

    public static Student getMockStudent() {
        Student mock = new Student();

        mock.setUserId("STU-123456");
        mock.setFirstName("John");
        mock.setLastName("Doe");
        mock.setActive(true);
        mock.setEmail("test@mail.com");

        return mock;
    }

    public static Instructor getMockInstructor() {
        Instructor mock = new Instructor();

        mock.setUserId("INS-123456");
        mock.setFirstName("John");
        mock.setLastName("Doe");
        mock.setActive(true);
        mock.setEmail("test@mail.com");

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
