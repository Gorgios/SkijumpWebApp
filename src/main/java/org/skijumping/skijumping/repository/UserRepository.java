package org.skijumping.skijumping.repository;

import org.skijumping.skijumping.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
}
