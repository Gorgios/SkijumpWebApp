package org.skijumping.skijumping.repository;

import org.skijumping.skijumping.model.Clasification;
import org.skijumping.skijumping.model.Jumper;
import org.skijumping.skijumping.model.Tournee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ClasificationRepository extends JpaRepository<Clasification,Jumper>  {
    Clasification findByJumperAndTournee (Jumper jumper, Tournee tournee);
    List<Clasification> findAllByTournee (Tournee tournee);
}
