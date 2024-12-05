package com.clutchacademy.user_service.dtos;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FailResponseTests {

    @Test
    public void testNoArgsConstructor() {
        FailResponse<String> response = new FailResponse<>();
        assertNull(response.getStatus());
        assertNull(response.getMessage());
        assertNull(response.getErrors());
    }

    @Test
    public void testAllArgsConstructor() {
        List<String> errors = Arrays.asList("Error1", "Error2");
        FailResponse<String> response = new FailResponse<>("FAIL", "Something went wrong", errors);
        assertEquals("FAIL", response.getStatus());
        assertEquals("Something went wrong", response.getMessage());
        assertEquals(errors, response.getErrors());
    }

    @Test
    public void testPartialConstructor() {
        FailResponse<String> response = new FailResponse<>("FAIL", "Something went wrong");
        assertEquals("FAIL", response.getStatus());
        assertEquals("Something went wrong", response.getMessage());
        assertNull(response.getErrors());
    }

    @Test
    public void testBuilder() {
        List<String> errors = Arrays.asList("Error1", "Error2");
        FailResponse<String> response = FailResponse.<String>builder()
                .status("FAIL")
                .message("Something went wrong")
                .errors(errors)
                .build();
        assertEquals("FAIL", response.getStatus());
        assertEquals("Something went wrong", response.getMessage());
        assertEquals(errors, response.getErrors());
    }

    @Test
    public void testSettersAndGetters() {
        FailResponse<String> response = new FailResponse<>();
        response.setStatus("FAIL");
        response.setMessage("Something went wrong");
        List<String> errors = Arrays.asList("Error1", "Error2");
        response.setErrors(errors);

        assertEquals("FAIL", response.getStatus());
        assertEquals("Something went wrong", response.getMessage());
        assertEquals(errors, response.getErrors());
    }
}