package org.imfreco.msvc.courses.controllers;

import jakarta.validation.Valid;
import org.imfreco.msvc.courses.models.Course;
import org.imfreco.msvc.courses.services.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        if(result.hasErrors()) {
            return validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id) {
        if(result.hasErrors()) {
            return validate(result);
        }
        Optional<Course> courseFound = courseService.getById(id);
        if(courseFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Course currentCourse = courseFound.get();
        currentCourse.setName(course.getName());
        return ResponseEntity.ok(courseService.save(currentCourse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> delete(@PathVariable Long id) {
        Optional<Course> courseFound = courseService.getById(id);
        if(courseFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), String.format("Field %s %s", error.getField(), error.getDefaultMessage()));
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
