package org.imfreco.msvc.courses.repositories;

import org.imfreco.msvc.courses.models.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
