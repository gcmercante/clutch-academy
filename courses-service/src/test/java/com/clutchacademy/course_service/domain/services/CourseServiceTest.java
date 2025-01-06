package com.clutchacademy.course_service.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.clutchacademy.course_service.domain.dtos.CreateCourseRequest;
import com.clutchacademy.course_service.domain.dtos.UpdateCourseRequest;
import com.clutchacademy.course_service.domain.dtos.User;
import com.clutchacademy.course_service.domain.enums.Status;
import com.clutchacademy.course_service.domain.models.Course;
import com.clutchacademy.course_service.domain.repositories.CoursesRepository;
import com.clutchacademy.course_service.exceptions.NotFoundException;
import com.clutchacademy.course_service.utils.CourseMock;
import com.clutchacademy.course_service.utils.InstructorMock;
import com.clutchacademy.course_service.utils.CourseRequestMock;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    private final Course course = CourseMock.getMockCourse();
    private final User instructor = InstructorMock.getMockInstructor();
    private final CreateCourseRequest createRequest = CourseRequestMock.getMockCreateCourseRequest();
    private final UpdateCourseRequest updateRequest = CourseRequestMock.getMockUpdateCourseRequest();

    @Mock
    CoursesRepository coursesRepository;

    @Mock
    UserService userService;

    @InjectMocks
    private CourseService courseService;

    @Nested
    class CreateCourse {
        @Test
        void shouldCreateCourse() {
            when(userService.getUserById(course.getOwner().getUserId())).thenReturn(instructor);
            when(coursesRepository.save(any(Course.class))).thenReturn(course);

            Course result = courseService.create(createRequest);

            verify(userService, times(1)).getUserById(course.getOwner().getUserId());
            verify(coursesRepository, times(1)).save(any(Course.class));

            assertThat(result).hasFieldOrProperty("id");
            assertThat(result.getTitle()).isEqualTo(course.getTitle());
            assertThat(result.getStatus()).isEqualTo(Status.ACTIVE);
        }
    }

    @Nested
    class UpdateCourse {
        @Test
        void shouldUpdateCourse() {
            final String id = course.getId();
            when(coursesRepository.findById(id)).thenReturn(Optional.of(course));
            when(coursesRepository.save(any(Course.class))).thenReturn(course);

            Course result = courseService.update(id.toString(), updateRequest);

            verify(coursesRepository, times(1)).findById(id);
            verify(coursesRepository, times(1)).save(any(Course.class));

            assertThat(result).hasFieldOrProperty("id");
            assertThat(result.getTitle()).isEqualTo(updateRequest.getTitle());
            assertThat(result.getDescription()).isEqualTo(updateRequest.getDescription());
        }

        @Test
        void shouldThrowNotFoundExceptionWhenCourseNotFound() {
            final String id = course.getId();
            when(coursesRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> courseService.update(id.toString(), updateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Course not found");

            verify(coursesRepository, times(1)).findById(id);
            verify(coursesRepository, times(0)).save(any(Course.class));
        }
    }

    @Nested
    class DisableCourse {
        @Test
        void shouldDisableCourse() {
            final String id = course.getId();

            when(coursesRepository.findById(id)).thenReturn(Optional.of(course));
            when(coursesRepository.save(any(Course.class))).thenAnswer(invocation -> invocation.getArgument(0));

            Course result = courseService.disable(id.toString());

            verify(coursesRepository, times(1)).findById(id);
            verify(coursesRepository, times(1)).save(any(Course.class));

            assertThat(result.getId()).isEqualTo(course.getId());
            assertThat(result.getStatus()).isEqualTo(Status.INACTIVE);
        }

        @Test
        void shouldThrowNotFoundExceptionWhenCourseNotFound() {
            final String id = course.getId();
            when(coursesRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> courseService.disable(id.toString()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Course not found");

            verify(coursesRepository, times(1)).findById(id);
            verify(coursesRepository, times(0)).save(any(Course.class));
        }
    }

    @Nested
    class EnableCourse {
        @Test
        void shouldDisableCourse() {
            final String id = course.getId();

            when(coursesRepository.findById(id)).thenReturn(Optional.of(course));
            when(coursesRepository.save(any(Course.class))).thenAnswer(invocation -> invocation.getArgument(0));

            Course result = courseService.enable(id.toString());

            verify(coursesRepository, times(1)).findById(id);
            verify(coursesRepository, times(1)).save(any(Course.class));

            assertThat(result.getId()).isEqualTo(course.getId());
            assertThat(result.getStatus()).isEqualTo(Status.ACTIVE);
        }

        @Test
        void shouldThrowNotFoundExceptionWhenCourseNotFound() {
            final String id = course.getId();
            when(coursesRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> courseService.enable(id.toString()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Course not found");

            verify(coursesRepository, times(1)).findById(id);
            verify(coursesRepository, times(0)).save(any(Course.class));
        }
    }

    @Nested
    class FindCourseById {
        @Test
        void shouldFindCourseById() {
            final String id = course.getId();

            when(coursesRepository.findById(id)).thenReturn(Optional.of(course));

            Course result = courseService.findById(id.toString());

            verify(coursesRepository, times(1)).findById(id);

            assertThat(result).isEqualTo(course);
        }

        @Test
        void shouldThrowNotFoundExceptionWhenCourseNotFound() {
            final String id = course.getId();
            when(coursesRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> courseService.findById(id.toString()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Course not found");

            verify(coursesRepository, times(1)).findById(id);
        }
    }

    @Nested
    class FindAllCourses {
        @Test
        void shouldFindAllCourses() {
            final List<Course> courses = List.of(course, course, course);
            when(coursesRepository.findAll()).thenReturn(courses);

            List<Course> result = courseService.find();

            assertThat(result).isNotEmpty();
            assertThat(result).isEqualTo(courses);
        }

        @Test
        void shouldReturnEmptyListWhenNoCoursesFound() {
            when(coursesRepository.findAll()).thenReturn(List.of());

            List<Course> result = courseService.find();

            assertThat(result).isEmpty();
        }
    }
}
