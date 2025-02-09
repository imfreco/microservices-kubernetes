package org.imfreco.msvc.courses.controllers;

import feign.FeignException;
import jakarta.validation.Valid;
import org.imfreco.msvc.courses.models.Course;
import org.imfreco.msvc.courses.models.User;
import org.imfreco.msvc.courses.services.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private ICourseService courseService;

    @GetMapping
    public List<Course> getAll() {
        return courseService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id) {
        Optional<Course> courseFound = courseService.getById(id);
        return courseFound.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Course course, BindingResult result) {
        if (result.hasErrors()) {
            return validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validate(result);
        }
        Optional<Course> courseFound = courseService.getById(id);
        if (courseFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Course currentCourse = courseFound.get();
        currentCourse.setName(course.getName());
        return ResponseEntity.ok(courseService.save(currentCourse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> delete(@PathVariable Long id) {
        Optional<Course> courseFound = courseService.getById(id);
        if (courseFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/course-user/{userId}")
    public ResponseEntity<?> deleteCourseUser(@PathVariable Long userId) {
        courseService.deleteCourseUserByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{courseId}/assign-user")
    public ResponseEntity<?> assignUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> userAssigned;
        try {
            userAssigned = courseService.assignUserToCourse(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("message", "user cannot assign to course"));
        }
        if (userAssigned.isPresent()) {
            return ResponseEntity.ok(userAssigned.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{courseId}/unassign-user")
    public ResponseEntity<?> unassignUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> userUnassigned;
        try {
            userUnassigned = courseService.unassignUserFromCourse(user, courseId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("message", "user cannot unassign from course"));
        }
        if (userUnassigned.isPresent()) {
            return ResponseEntity.ok(userUnassigned.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{courseId}/create-assign-user")
    public ResponseEntity<?> createUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> userCreated;
        try {
            userCreated = courseService.createUserToCourse(user, courseId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("message", "user cannot create"));
        }
        if (userCreated.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated.get());
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), String.format("Field %s %s", error.getField(), error.getDefaultMessage()));
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
