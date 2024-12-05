package com.clutchacademy.user_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = UserServiceApplication.class)
class UserServiceApplicationTests {
    @Test
    void contextLoads() {
    }

    @Test
    void main() {
        UserServiceApplication.main(new String[] {});
    }
}
