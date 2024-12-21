package com.clutchacademy.user_service.domain.services;

import com.clutchacademy.user_service.domain.dtos.UpdateUser;
import com.clutchacademy.user_service.domain.dtos.UserRequest;
import com.clutchacademy.user_service.domain.dtos.UserResponse;
import com.clutchacademy.user_service.domain.enums.UserType;
import com.clutchacademy.user_service.domain.models.User;
import com.clutchacademy.user_service.domain.repositories.UserRepository;
import com.clutchacademy.user_service.exceptions.HttpNotFoundException;

import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(
            UserRepository studentRepository
    ) {
        this.userRepository = studentRepository;
    }

    public UserResponse create(UserRequest user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (user.getUserType() == null) {
            throw new IllegalArgumentException("User type must be defined");
        }

        Optional<User> optionalUser= userRepository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()) {
            throw new EntityExistsException("User with email " + user.getEmail() + " already exists");
        }

        if (user.getUserType() != UserType.STUDENT && user.getUserType() != UserType.INSTRUCTOR) {
            throw new IllegalArgumentException("Unsupported User type: " + user.getUserType());
        }

        User newUser = new User();
        mapUserFields(user, newUser);

        return mapUserResponse(userRepository.save(newUser));
    }

    
    public UserResponse update(String userId, UpdateUser updateUser) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);

        if (optionalUser.isEmpty()) {
           throw new HttpNotFoundException("User with ID " + userId + " not found");
        }

        User user = optionalUser.get();
        
        Field[] fields = UpdateUser.class.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(updateUser);

                if(value != null) {
                    Field userField = User.class.getDeclaredField(field.getName());
                    userField.setAccessible(true);
                    userField.set(user, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException exception) {
                throw new RuntimeException("Error updating user field: " + field.getName(), exception);
            }
        }

        return mapUserResponse(userRepository.save(user));
    }

    public UserResponse findById(String userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);

        if (optionalUser.isEmpty()) {
            throw new HttpNotFoundException("User with ID " + userId + " not found");
        }

        User user = optionalUser.get();

        return mapUserResponse(user);
    }

    public User disable(String userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);

        if (optionalUser.isEmpty()) {
            throw new HttpNotFoundException("User with ID " + userId + " not found");
        }

        User user = optionalUser.get();
        user.setActive(false);

        return userRepository.save(user);
    }

    public List<UserResponse> find() {
        return userRepository.findAll().stream().map(this::mapUserResponse).collect(Collectors.toList());
    }

    private void mapUserFields(UserRequest source, User target) {
        target.setUserId(generateUserId(source.getUserType()));
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setActive(true);
        target.setEmail(source.getEmail());
        target.setType(source.getUserType());
    }

    private UserResponse mapUserResponse(User source) {
        return UserResponse
                .builder()
                .userId(source.getUserId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .active(source.getActive())
                .email(source.getEmail())
                .build();
    }

    private String generateUserId(UserType userType) {
        String prefix = userType == UserType.STUDENT ? "STU-" : "INS-";
        return prefix + UUID.randomUUID();
    }
}
