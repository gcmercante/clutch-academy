package com.clutchacademy.user_service.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import com.clutchacademy.user_service.domain.dtos.SuccessResponse;

public class SuccessResponseTests {

    @Test
    public void testSuccessResponseConstructor() {
        SuccessResponse.ResponseData<String> responseData = new SuccessResponse.ResponseData<>("resource");
        SuccessResponse<String> successResponse = new SuccessResponse<>("success", responseData);

        assertEquals("success", successResponse.getStatus());
        assertEquals("resource", successResponse.getData().getResource());
    }

    @Test
    public void testResponseDataConstructor() {
        SuccessResponse.ResponseData<String> responseData = new SuccessResponse.ResponseData<>("resource");

        assertNull(responseData.getId());
        assertEquals("resource", responseData.getResource());
    }

    @Test
    public void testResponseDataAllArgsConstructor() {
        SuccessResponse.ResponseData<String> responseData = new SuccessResponse.ResponseData<>("id", "resource");

        assertEquals("id", responseData.getId());
        assertEquals("resource", responseData.getResource());
    }

    @Test
    public void testResponseDataNoArgsConstructor() {
        SuccessResponse.ResponseData<String> responseData = new SuccessResponse.ResponseData<>();

        assertNull(responseData.getId());
        assertNull(responseData.getResource());
    }

    @Test
    public void testSuccessResponseBuilder() {
        SuccessResponse.ResponseData<String> responseData = SuccessResponse.ResponseData.<String>builder()
                .id("id")
                .resource("resource")
                .build();

        SuccessResponse<String> successResponse = SuccessResponse.<String>builder()
                .status("success")
                .data(responseData)
                .build();

        assertEquals("success", successResponse.getStatus());
        assertEquals("id", successResponse.getData().getId());
        assertEquals("resource", successResponse.getData().getResource());
    }
}