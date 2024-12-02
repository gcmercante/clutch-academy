package com.clutchacademy.user_service.services;

import com.clutchacademy.user_service.dtos.UpdateUser;
import com.clutchacademy.user_service.enums.UserType;
import com.clutchacademy.user_service.models.Instructor;
import com.clutchacademy.user_service.models.Student;
import com.clutchacademy.user_service.models.User;
import com.clutchacademy.user_service.repositories.InstructorRepository;
import com.clutchacademy.user_service.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public User create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (user.getType() == null) {
            throw new IllegalArgumentException("User type must be defined");
        }

        // TODO: Change that switch to a Factory
        switch (user.getType()) {
            case STUDENT -> {
                Student student = new Student();
                mapCommonFields(user, student);

                return studentRepository.save(student);
            }

            case INSTRUCTOR -> {
                Instructor instructor = new Instructor();
                mapCommonFields(user, instructor);

                return instructorRepository.save(instructor);
            }

            default -> throw new IllegalArgumentException("Unsupported User type: " + user.getType());
        }
    }

    public User update(String userId, UpdateUser updateUser) {
        Optional<User> optionalUser = studentRepository.findByUserId(userId)
                .map(user -> (User) user)
                .or(() -> instructorRepository.findByUserId(userId));

        if (optionalUser.isEmpty()) {
           throw new IllegalArgumentException("User with ID " + userId + " not found");
        }

        User user = optionalUser.get();
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());

        if (user instanceof Student) {
            return studentRepository.save((Student) user);
        } else {
            return instructorRepository.save((Instructor) user);
        }
    }

    public User findById(String userId) {
        Optional<User> optionalUser = studentRepository.findByUserId(userId)
                .map(user -> (User) user)
                .or(() -> instructorRepository.findByUserId(userId));

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }

        return optionalUser.get();
    }

    public User disable(String userId) {
        Optional<User> optionalUser = studentRepository.findByUserId(userId)
                .map(user -> (User) user)
                .or(() -> instructorRepository.findByUserId(userId));

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }

        User user = optionalUser.get();
        user.setActive(false);

        if (user instanceof Student) {
            return studentRepository.save((Student) user);
        } else {
            return instructorRepository.save((Instructor) user);
        }
    }

    public List<User> find() {
        List<Student> students = studentRepository.findAll();
        List<Instructor> instructors = instructorRepository.findAll();

        return Stream.concat(students.stream(), instructors.stream())
                .collect(Collectors.toList());
    }

    private void mapCommonFields(User source, User target) {
        target.setUserId(generateUserId(source.getType()));
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setActive(true);
    }

    private String generateUserId(UserType userType) {
        String prefix = userType == UserType.STUDENT ? "STU-" : "INS-";
        return prefix + UUID.randomUUID().toString().substring(0, 8);
    }
}
