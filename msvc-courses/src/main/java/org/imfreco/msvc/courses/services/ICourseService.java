package org.imfreco.msvc.courses.services;

import org.imfreco.msvc.courses.models.Course;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    List<Course> getAll();
    Optional<Course> getById(Long id);
    Course save(Course course);
    void delete(Long id);
}
