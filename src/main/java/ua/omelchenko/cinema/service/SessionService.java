package ua.omelchenko.cinema.service;

import org.springframework.data.domain.Page;
import ua.omelchenko.cinema.entity.Session;

import java.util.Optional;

public interface SessionService {
    Page<Session> getPagingSessions(Integer page, String sort, Boolean limitPlaces);

    Optional<Session> getSessionById(long sessionId);

    void updateNumberOfTickets(int places, Session session);

    boolean createSession(Session session);

    void removeSession(Long sessionId);
}
