package ua.omelchenko.cinema.jdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.omelchenko.cinema.entity.Session;
import ua.omelchenko.cinema.entity.Ticket;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional <List<Ticket>> findAllBySessionId(Session session);
}
