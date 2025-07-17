package edu.fbansept.m2i2.dao;

import edu.fbansept.m2i2.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketDao extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByResoluFalse();
}
