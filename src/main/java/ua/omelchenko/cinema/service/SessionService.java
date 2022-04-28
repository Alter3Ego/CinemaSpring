package ua.omelchenko.cinema.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.omelchenko.cinema.entity.Session;
import ua.omelchenko.cinema.jdbc.repository.SessionRepository;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;


@Log4j2
@Service
public class SessionService {

    @Value("20")
    private Integer HALL_CAPACITY;
    @Value("50")
    private Integer HALL_OVER_CAPACITY;
    @Value("4")
    private Integer NUMBERS_OF_CELLS;

    @Autowired
    private TicketService ticketService;
    @Autowired
    ObjectFactory<HttpSession> httpSessionFactory;

    final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

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
        int pageNumber;
        pageNumber = !Objects.isNull(page) ? page : 0;
        Pageable pageable = PageRequest.of(pageNumber, NUMBERS_OF_CELLS, Sort.by(paginationSort));

        return sessionRepository.findAllByNumberOfTicketsIsLessThan(limit ? HALL_CAPACITY : HALL_OVER_CAPACITY, pageable);
    }

    public Optional<Session> getSessionById(long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public boolean updateNumberOfTickets(int places, Session session) {
        return sessionRepository.updateNumberOfTickets(places, session) == 1;

    }

    public boolean createSession(Session session) {
        if (session != null) {
            session.setNumberOfTickets(0);
            sessionRepository.save(session);
            return true;
        }
        return false;

    }

    @Transactional
    public boolean removeSession(Long sessionId) {
        if (sessionId != null) {
            Optional<Session> sessionOptional = getSessionById(sessionId);
            if (sessionOptional.isPresent()) {
                Session session = sessionOptional.get();
                ticketService.removeTicketsBySession(session);
                sessionRepository.delete(session);
            }

            return true;
        }
        return false;
    }


}



