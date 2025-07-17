package edu.fbansept.m2i2.dao;

import edu.fbansept.m2i2.model.Priorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrioriteDao extends JpaRepository<Priorite, Integer> {
}
