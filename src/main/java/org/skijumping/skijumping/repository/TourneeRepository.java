package org.skijumping.skijumping.repository;

import org.skijumping.skijumping.model.Competition;
import org.skijumping.skijumping.model.Tournee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TourneeRepository extends CrudRepository <Tournee,Integer> {
}
