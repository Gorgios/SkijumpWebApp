package org.skijumping.skijumping.repository;

import org.skijumping.skijumping.model.Clasification;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface ClasificationRepository extends CrudRepository<Clasification, Integer> {
}
