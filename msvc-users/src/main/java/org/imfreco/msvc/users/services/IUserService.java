package org.imfreco.msvc.users.services;

import org.imfreco.msvc.users.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAll();

    Optional<User> getById(Long id);

    List<User> getAllByIds(Iterable<Long> ids);

    User save(User user);

    void delete(Long id);

    Optional<User> getByEmail(String email);
}
