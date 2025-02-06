package org.imfreco.msvc.users.controllers;

import org.imfreco.msvc.users.models.User;
import org.imfreco.msvc.users.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        Optional<User> userFound = userService.getById(id);
        return userFound.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable Long id) {
        Optional<User> userFound = userService.getById(id);
        if (userFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User currentUser = userFound.get();
        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        return ResponseEntity.ok(userService.save(currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) {
        Optional<User> userFound = userService.getById(id);
        if (userFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
