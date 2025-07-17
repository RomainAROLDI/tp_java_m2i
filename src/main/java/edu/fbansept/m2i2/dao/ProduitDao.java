package edu.fbansept.m2i2.dao;

import edu.fbansept.m2i2.model.Produit;
import edu.fbansept.m2i2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitDao extends JpaRepository<Produit, Integer> {
}
