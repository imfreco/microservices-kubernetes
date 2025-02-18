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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<Course> courseFound = courseRepository.findById(id);
        if (courseFound.isPresent()) {
            Course currentCourse = courseFound.get();
            List<Long> userIds = currentCourse.getCourseUsers().stream()
                    .map(CourseUser::getUserId).collect(Collectors.toList());
            currentCourse.setUsers(userClient.getAllByIds(userIds));
            return Optional.of(currentCourse);
        }
        return Optional.empty();
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
    public void deleteCourseUserByUserId(Long userId) {
        courseRepository.deleteCourseUserByUserId(userId);
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
            Optional<CourseUser> courseUserToDelete = currentCourse.getCourseUsers().stream()
                    .filter(courseUser -> Objects.equals(courseUser.getUserId(), userFound.getId())).findFirst();
            if (courseUserToDelete.isPresent()) {
                currentCourse.removeCourseUser(courseUserToDelete.get());
                courseRepository.save(currentCourse);
            }
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
