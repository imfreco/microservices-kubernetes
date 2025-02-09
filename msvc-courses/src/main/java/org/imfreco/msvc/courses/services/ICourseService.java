package org.imfreco.msvc.courses.services;

import org.imfreco.msvc.courses.models.Course;
import org.imfreco.msvc.courses.models.User;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    List<Course> getAll();
    Optional<Course> getById(Long id);
    Course save(Course course);
    void delete(Long id);

    Optional<User> assignUserToCourse(User user, Long courseId);
    Optional<User> unassignUserFromCourse(User user, Long courseId);
    Optional<User> createUserToCourse(User user, Long courseId);
}
