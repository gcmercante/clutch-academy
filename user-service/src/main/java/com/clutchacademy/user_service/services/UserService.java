package com.clutchacademy.user_service.services;

import com.clutchacademy.user_service.dtos.UpdateUser;
import com.clutchacademy.user_service.dtos.UserRequest;
import com.clutchacademy.user_service.dtos.UserResponse;
import com.clutchacademy.user_service.enums.UserType;
import com.clutchacademy.user_service.exceptions.HttpNotFoundException;
import com.clutchacademy.user_service.models.Instructor;
import com.clutchacademy.user_service.models.Student;
import com.clutchacademy.user_service.models.User;
import com.clutchacademy.user_service.repositories.InstructorRepository;
import com.clutchacademy.user_service.repositories.StudentRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    @Autowired
    public UserService(
            StudentRepository studentRepository,
            InstructorRepository instructorRepository
    ) {
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
    }

    public UserResponse create(UserRequest user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (user.getUserType() == null) {
            throw new IllegalArgumentException("User type must be defined");
        }

        Optional<User> optionalUser= studentRepository.findByEmail(user.getEmail())
                .or(() -> instructorRepository.findByUserId(user.getEmail()));

        if (optionalUser.isPresent()) {
            throw new EntityExistsException("User with email " + user.getEmail() + " already exists");
        }

        switch (user.getUserType()) {
            case STUDENT -> {
                Student student = new Student();
                mapCommonFields(user, student);

                return mapUserResponse(studentRepository.save(student));
            }

            case INSTRUCTOR -> {
                Instructor instructor = new Instructor();
                mapCommonFields(user, instructor);

                return mapUserResponse(instructorRepository.save(instructor));
            }

            default -> throw new IllegalArgumentException("Unsupported User type: " + user.getUserType());
        }
    }

    
    public UserResponse update(String userId, UpdateUser updateUser) {
        Optional<User> optionalUser = studentRepository.findByUserId(userId)
                .map(user -> (User) user)
                .or(() -> instructorRepository.findByUserId(userId));

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

        if (user instanceof Student) {
            return mapUserResponse(studentRepository.save((Student) user));
        } else {
            return mapUserResponse(instructorRepository.save((Instructor) user));
        }
    }

    public UserResponse findById(String userId) {
        Optional<User> optionalUser = studentRepository.findByUserId(userId)
                .map(user -> (User) user)
                .or(() -> instructorRepository.findByUserId(userId));

        if (optionalUser.isEmpty()) {
            throw new HttpNotFoundException("User with ID " + userId + " not found");
        }

        User user = optionalUser.get();

        return mapUserResponse(user);
    }

    public User disable(String userId) {
        Optional<User> optionalUser = studentRepository.findByUserId(userId)
                .map(user -> (User) user)
                .or(() -> instructorRepository.findByUserId(userId));

        if (optionalUser.isEmpty()) {
            throw new HttpNotFoundException("User with ID " + userId + " not found");
        }

        User user = optionalUser.get();
        user.setActive(false);

        if (user instanceof Student) {
            return studentRepository.save((Student) user);
        } else {
            return instructorRepository.save((Instructor) user);
        }
    }

    public List<UserResponse> find() {
        List<Student> students = studentRepository.findAll();
        List<Instructor> instructors = instructorRepository.findAll();

        return Stream.concat(students.stream().map(this::mapUserResponse),
                instructors.stream().map(this::mapUserResponse)).collect(Collectors.toList());
    }

    private void mapCommonFields(UserRequest source, User target) {
        target.setUserId(generateUserId(source.getUserType()));
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setActive(true);
        target.setEmail(source.getEmail());
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
