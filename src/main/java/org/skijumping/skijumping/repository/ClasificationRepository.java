package org.skijumping.skijumping.repository;

import org.skijumping.skijumping.model.Clasification;
import org.skijumping.skijumping.model.Jumper;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ClasificationRepository extends CrudRepository<Clasification, Integer> {
    Clasification findClasificationByJumper (Jumper jumper);
}
