package org.imfreco.msvc.users.repositories;

import org.imfreco.msvc.users.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
