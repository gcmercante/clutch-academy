package com.clutchacademy.user_service.controllers;

import com.clutchacademy.user_service.dtos.UpdateUser;
import com.clutchacademy.user_service.dtos.UserRequest;
import com.clutchacademy.user_service.dtos.UserResponse;
import com.clutchacademy.user_service.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody
            @Valid
            UserRequest user
    ) {
        userService.create(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<UserResponse> find() {
        return userService.find();
    }

    @GetMapping("/{userId}")
    public UserResponse findById(@PathVariable String userId) {
        return userService.findById(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> update(
            @PathVariable
            String userId,
            @Valid
            @RequestBody
            UpdateUser userToUpdate
    ) {
        userService.update(userId, userToUpdate);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/disable/{userId}")
    public ResponseEntity<Void> disable(@PathVariable String userId) {
        userService.disable(userId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
