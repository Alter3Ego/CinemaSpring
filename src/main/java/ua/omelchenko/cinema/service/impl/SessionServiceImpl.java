package ua.omelchenko.cinema.service.impl;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.omelchenko.cinema.entity.Session;
import ua.omelchenko.cinema.repository.SessionRepository;
import ua.omelchenko.cinema.service.SessionService;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    @Value("20")
    private Integer HALL_CAPACITY;
    @Value("50")
    private Integer HALL_OVER_CAPACITY;
    @Value("4")
    private Integer NUMBERS_OF_CELLS;


    private TicketServiceImpl ticketService;
    private final ObjectFactory<HttpSession> httpSessionFactory;

    @Autowired
    public void setTicketService(TicketServiceImpl ticketService) {
        this.ticketService = ticketService;
    }

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, ObjectFactory<HttpSession> httpSessionFactory) {
        this.sessionRepository = sessionRepository;
        this.httpSessionFactory = httpSessionFactory;
    }

    @Override
    public Page<Session> getPagingSessions(Integer page, String sort, Boolean limitPlaces) {

        HttpSession httpSession = httpSessionFactory.getObject();

        if (!Objects.isNull(limitPlaces)) {
            httpSession.setAttribute("limitPlaces", limitPlaces);
        }

        boolean limit = (!Objects.isNull(httpSession.getAttribute("limitPlaces")) &&
                (boolean) httpSession.getAttribute("limitPlaces"));

        String paginationSort = "dateTime";
        if (sort != null) {
            switch (sort) {
                case "dateTime" -> httpSession.setAttribute("sort", "dateTime");
                case "name" -> httpSession.setAttribute("sort", "film.title");
                case "places" -> httpSession.setAttribute("sort", "numberOfTickets");
            }
        }
        if (!Objects.isNull(httpSession.getAttribute("sort"))) {
            paginationSort = (String) httpSession.getAttribute("sort");
        }

        int pageNumber = !Objects.isNull(page) ? page : 0;
        Pageable pageable = PageRequest.of(pageNumber, NUMBERS_OF_CELLS, Sort.by(paginationSort));

        return sessionRepository.findAllByNumberOfTicketsIsLessThan(limit ? HALL_CAPACITY : HALL_OVER_CAPACITY, pageable);
    }

    @Override
    public Optional<Session> getSessionById(long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    @Override
    public void updateNumberOfTickets(int places, Session session) {
        sessionRepository.updateNumberOfTickets(places, session);

    }

    @Override
    public boolean createSession(Session session) {
        if (session != null) {
            session.setNumberOfTickets(0);
            sessionRepository.save(session);
            return true;
        }
        return false;

    }

    @Override
    @Transactional
    public void removeSession(Long sessionId) {
        if (sessionId != null) {
            Optional<Session> sessionOptional = getSessionById(sessionId);
            if (sessionOptional.isPresent()) {
                Session session = sessionOptional.get();
                ticketService.removeTicketsBySession(session);
                sessionRepository.delete(session);
            }
        }
    }
}



