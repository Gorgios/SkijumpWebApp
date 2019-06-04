package org.skijumping.skijumping.repository;

import org.skijumping.skijumping.model.Competition;
import org.skijumping.skijumping.model.Start;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StartRepository extends CrudRepository<Start,Integer> {
    List<Start>  findAllByCompetition (Competition competition);
}
