package org.imfreco.msvc.courses.clients;

import org.imfreco.msvc.courses.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-users", url = "${users.client.url}")
public interface UserRestClient {
    @GetMapping("/{id}")
    User getById(@PathVariable Long id);

    @GetMapping("/by-ids")
    List<User> getAllByIds(@RequestParam List<Long> ids);

    @PostMapping
    User create(@RequestBody User user);
}
