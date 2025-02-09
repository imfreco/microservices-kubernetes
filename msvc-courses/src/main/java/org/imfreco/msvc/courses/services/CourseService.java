package org.imfreco.msvc.courses.services;

import org.imfreco.msvc.courses.clients.UserRestClient;
import org.imfreco.msvc.courses.models.Course;
import org.imfreco.msvc.courses.models.CourseUser;
import org.imfreco.msvc.courses.models.User;
import org.imfreco.msvc.courses.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements ICourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRestClient userClient;

    @Override
    @Transactional(readOnly = true)
    public List<Course> getAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> getById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> assignUserToCourse(User user, Long courseId) {
        Optional<Course> courseFound = courseRepository.findById(courseId);
        if (courseFound.isPresent()) {
            User userFound = userClient.getById(user.getId());
            Course currentCourse = courseFound.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userFound.getId());
            currentCourse.addCourseUser(courseUser);
            courseRepository.save(currentCourse);
            return Optional.of(userFound);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> unassignUserFromCourse(User user, Long courseId) {
        Optional<Course> courseFound = courseRepository.findById(courseId);
        if (courseFound.isPresent()) {
            User userFound = userClient.getById(user.getId());
            Course currentCourse = courseFound.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userFound.getId());
            currentCourse.removeCourseUser(courseUser);
            courseRepository.save(currentCourse);
            return Optional.of(userFound);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUserToCourse(User user, Long courseId) {
        Optional<Course> courseFound = courseRepository.findById(courseId);
        if (courseFound.isPresent()) {
            User userCreated = userClient.create(user);
            Course currentCourse = courseFound.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userCreated.getId());
            currentCourse.addCourseUser(courseUser);
            courseRepository.save(currentCourse);
            return Optional.of(userCreated);
        }
        return Optional.empty();
    }
}
