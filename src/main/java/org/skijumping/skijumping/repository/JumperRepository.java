package org.skijumping.skijumping.repository;

import org.skijumping.skijumping.model.Jumper;
import org.skijumping.skijumping.model.User;
import org.springframework.data.repository.CrudRepository;

public interface JumperRepository extends CrudRepository<Jumper,Integer> {
    Jumper findByUser (User user);
}
