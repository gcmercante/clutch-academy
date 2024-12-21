package com.clutchacademy.user_service.controllers;

import com.clutchacademy.user_service.domain.dtos.SuccessResponse;
import com.clutchacademy.user_service.domain.dtos.UpdateUser;
import com.clutchacademy.user_service.domain.dtos.UserRequest;
import com.clutchacademy.user_service.domain.dtos.UserResponse;
import com.clutchacademy.user_service.domain.services.UserService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<Object>> create(
            @RequestBody
            @Valid
            UserRequest user
    ) {
        UserResponse result = userService.create(user);

        return responseWithId(HttpStatus.CREATED, result);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<Object>> find() {
        List<UserResponse> result = userService.find();

        return batchResponse(HttpStatus.OK, result);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse<Object>> findById(@PathVariable String userId) {
        UserResponse result = userService.findById(userId);

        return responseWithId(HttpStatus.OK, result);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<SuccessResponse<Object>> update(
            @PathVariable
            String userId,
            @Valid
            @RequestBody
            UpdateUser userToUpdate
    ) {
        UserResponse result = userService.update(userId, userToUpdate);

        return responseWithId(HttpStatus.OK, result);
    }

    @PutMapping("/disable/{userId}")
    public ResponseEntity<Void> disable(@PathVariable String userId) {
        userService.disable(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private ResponseEntity<SuccessResponse<Object>> responseWithId(HttpStatus status, UserResponse result) {
        SuccessResponse.ResponseData<Object> data = SuccessResponse.ResponseData.builder()
                .id(result.getUserId())
                .resource(result)
                .build();

        SuccessResponse<Object> response = SuccessResponse.builder()
                .status("success")
                .data(data)
                .build();

        return ResponseEntity.status(status).body(response);
    }

    private ResponseEntity<SuccessResponse<Object>> batchResponse(HttpStatus status, List<UserResponse> result) {
        SuccessResponse.ResponseData<Object> data = SuccessResponse.ResponseData.builder()
                .resource(result)
                .build();

        SuccessResponse<Object> response = SuccessResponse.builder()
                .status("success")
                .data(data)
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
