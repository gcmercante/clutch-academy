package com.clutchacademy.user_service.service;

import com.clutchacademy.user_service.dtos.UpdateUser;
import com.clutchacademy.user_service.dtos.UserRequest;
import com.clutchacademy.user_service.dtos.UserResponse;
import com.clutchacademy.user_service.enums.UserType;
import com.clutchacademy.user_service.exceptions.HttpNotFoundException;
import com.clutchacademy.user_service.models.User;
import com.clutchacademy.user_service.repositories.UserRepository;
import com.clutchacademy.user_service.services.UserService;
import com.clutchacademy.user_service.utils.MockUser;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    private final UserRequest studentPayload = MockUser.getMockUserRequest(MockUser.MockType.PAYLOAD, UserType.STUDENT);
    private final User student = MockUser.getMockUser(UserType.STUDENT);
    private final UserRequest instructorPayload = MockUser.getMockUserRequest(MockUser.MockType.PAYLOAD, UserType.INSTRUCTOR);
    private final User instructor = MockUser.getMockUser(UserType.INSTRUCTOR);
    private final UserRequest userTypeNull = MockUser.getMockUserRequest(MockUser.MockType.USER_TYPE_NULL, UserType.UNKNOWN);
    private final UserRequest userTypeUnsupported = MockUser.getMockUserRequest(MockUser.MockType.UNSUPPORTED_USER_TYPE, UserType.UNKNOWN);
    private final UpdateUser updateUser = MockUser.getMockUpdateUser();

    @InjectMocks
    private UserService userService;

    @Nested
    class CreateTests {
        @Test
        void create_ShouldCreateStudent_WhenUserTypeIsStudent() {
            when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

            UserResponse result = userService.create(studentPayload);

            assertThat(result.getUserId()).startsWith("STU-");
            assertThat(result.getActive()).isTrue();

            verify(userRepository, times(1)).save(any(User.class));
        }

        @Test
        void create_ShouldCreateInstructor_WhenUserTypeIsInstructor() {
            when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

            UserResponse result = userService.create(instructorPayload);

            assertThat(result.getUserId()).startsWith("INS-");
            assertThat(result.getActive()).isTrue();

            verify(userRepository, times(1)).save(any(User.class));
        }

        @Test
        void create_ShouldThrowIllegalArgumentException_WhenUserIsNull() {
            assertThatThrownBy(() -> userService.create(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("User cannot be null");

            verifyNoInteractions(userRepository);
        }

        @Test
        void create_ShouldThrowIllegalArgumentException_WhenUserTypeIsNull() {
            assertThatThrownBy(() -> userService.create(userTypeNull))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("User type must be defined");

            verifyNoInteractions(userRepository);
        }

        @Test
        void create_ShouldThrowIllegalArgumentException_WhenUserTypeIsUnsupported() {
            assertThatThrownBy(() -> userService.create(userTypeUnsupported))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Unsupported User type: " + userTypeUnsupported.getUserType());
        }
    }

    @Nested
    class UpdateTests {
        @Test
        void update_ShouldUpdateStudentNameSuccessfully() {
            when(userRepository.findByUserId(student.getUserId())).thenReturn(Optional.of(student));
            when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

            UserResponse updatedStudent = userService.update(student.getUserId(), updateUser);

            assertThat(updatedStudent.getFirstName()).isEqualTo(updateUser.getFirstName());
            assertThat(updatedStudent.getLastName()).isEqualTo(updateUser.getLastName());
            verify(userRepository, times(1)).save(student);
        }

        @Test
        void update_ShouldUpdateInstructorNameSuccessfully() {
            when(userRepository.findByUserId(instructor.getUserId())).thenReturn(Optional.of(instructor));
            when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

            UserResponse updatedInstructor = userService.update(instructor.getUserId(), updateUser);

            assertThat(updatedInstructor.getFirstName()).isEqualTo(updateUser.getFirstName());
            assertThat(updatedInstructor.getLastName()).isEqualTo(updateUser.getLastName());
            verify(userRepository, times(1)).save(instructor);
        }

        @Test
        void updateUser_ShouldThrowException_WhenUserNotFound() {
            when(userRepository.findByUserId("someId")).thenReturn(Optional.empty());
            when(userRepository.findByUserId("someId")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> userService.update("someId", updateUser))
                    .isInstanceOf(HttpNotFoundException.class)
                    .hasMessageContaining("User with ID someId not found");

            verify(userRepository, times(1)).findByUserId("someId");
            verify(userRepository, times(1)).findByUserId("someId");
        }

        @Test
        void testUpdateUserWithNullValue() {
            String userId = student.getUserId();
            updateUser.setFirstName(null); // Setting a field to null

            when(userRepository.findByUserId(userId)).thenReturn(Optional.of(student));
            when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

            UserResponse response = userService.update(userId, updateUser);

            assertNotNull(response);
            assertEquals(userId, response.getUserId());

            verify(userRepository, times(1)).findByUserId(userId);
            verify(userRepository, times(1)).save(student);
        }
    }

    @Nested
    class FindByIdTests {
        @Test
        void findById_ShouldReturnExistingStudent(){
            String userId = student.getUserId();
            when(userRepository.findByUserId(userId)).thenReturn(Optional.of(student));

            UserResponse returnedStudent = userService.findById(userId);

            assertThat(returnedStudent.getUserId()).isEqualTo(userId);
            assertThat(returnedStudent.getUserId()).startsWith("STU");
            assertThat(returnedStudent).isInstanceOf(UserResponse.class);

            verify(userRepository, times(1)).findByUserId(student.getUserId());
        }

        @Test
        void findById_ShouldReturnExistingInstructor(){
            String userId = instructor.getUserId();
            when(userRepository.findByUserId(userId)).thenReturn(Optional.of(instructor));

            UserResponse returnedStudent = userService.findById(userId);

            assertThat(returnedStudent.getUserId()).isEqualTo(userId);
            assertThat(returnedStudent.getUserId()).startsWith("INS");
            assertThat(returnedStudent).isInstanceOf(UserResponse.class);

            verify(userRepository, times(1)).findByUserId(instructor.getUserId());
        }

        @Test
        void findById_ShouldThrowHttpNotFoundException() {
            String userId = "anyId";
            when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());
            when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> userService.findById(userId))
                    .isInstanceOf(HttpNotFoundException.class)
                    .hasMessage("User with ID " + userId + " not found");

            verify(userRepository, times(1)).findByUserId(userId);
            verify(userRepository, times(1)).findByUserId(userId);
        }
    }
    
    @Nested
    class FindTests {
        @Test
        void find_ShouldReturnListOfUsers() {
            when(userRepository.findAll()).thenReturn(List.of(student, instructor));

            List<UserResponse> users = userService.find();

            assertThat(users).isNotEmpty();
            assertThat(users.size()).isEqualTo(2);
            assertThat(users).allMatch(Objects::nonNull);

            verify(userRepository, times(1)).findAll();
            verify(userRepository, times(1)).findAll();
        }

        @Test
        void find_ShouldReturnEmptyListOfUsers() {
            when(userRepository.findAll()).thenReturn(List.of());
            when(userRepository.findAll()).thenReturn(List.of());

            List<UserResponse> users = userService.find();

            assertThat(users).isEmpty();

            verify(userRepository, times(1)).findAll();
            verify(userRepository, times(1)).findAll();
        }
    }

    @Nested
    class DisableTests {
        @Test
        void disable_ShouldDeactivateStudentByUserId() {
            String userId = student.getUserId();
            when(userRepository.findByUserId(userId)).thenReturn(Optional.of(student));
            when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

            User deactivatedUser = userService.disable(userId);

            assertThat(deactivatedUser.getActive()).isFalse();
            assertThat(deactivatedUser.getUserId()).isEqualTo(userId);
            assertThat(deactivatedUser.getUserId()).startsWith("STU-");

            verify(userRepository, times(1)).findByUserId(userId);
            verify(userRepository, times(1)).save(any(User.class));
        }

        @Test
        void disable_ShouldDeactivateInstructorByUserId() {
            String userId = instructor.getUserId();
            when(userRepository.findByUserId(userId)).thenReturn(Optional.of(instructor));
            when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

            User deactivatedUser = userService.disable(userId);

            assertThat(deactivatedUser.getActive()).isFalse();
            assertThat(deactivatedUser.getUserId()).isEqualTo(userId);
            assertThat(deactivatedUser.getUserId()).startsWith("INS-");

            verify(userRepository, times(1)).findByUserId(userId);
            verify(userRepository, times(1)).save(any(User.class));
        }

        @Test
        void disable_ShouldThrowIllegalArgumentExceptionWhenUserIdNotFound() {
            String userId = "anyId";
            when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());
            when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> userService.disable(userId))
                    .isInstanceOf(HttpNotFoundException.class)
                    .hasMessage("User with ID " + userId + " not found");

            verify(userRepository, times(1)).findByUserId(userId);
            verify(userRepository, times(1)).findByUserId(userId);
        }
    }
}
