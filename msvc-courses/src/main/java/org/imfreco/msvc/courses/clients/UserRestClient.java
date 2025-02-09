package org.imfreco.msvc.courses.clients;

import org.imfreco.msvc.courses.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-users", url = "localhost:8001/users")
public interface UserRestClient {
    @GetMapping("/{id}")
    User getById(@PathVariable Long id);

    @PostMapping
    User create(@RequestBody User user);
}
