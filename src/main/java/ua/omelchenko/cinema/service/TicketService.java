package ua.omelchenko.cinema.service;

import ua.omelchenko.cinema.entity.Session;
import ua.omelchenko.cinema.entity.Ticket;
import ua.omelchenko.cinema.exception.LowBalanceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TicketService {
    Map<Integer, Long> getPlaces(Session session);

    void addTickets(List<Integer> places, Long sessionId) throws LowBalanceException;

    Optional<List<Ticket>> getTicketsBySessionId(Session session);

    void removeTicketsBySession(Session session);
}
