package ua.omelchenko.cinema.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.omelchenko.cinema.entity.Session;
import ua.omelchenko.cinema.entity.Ticket;
import ua.omelchenko.cinema.entity.User;
import ua.omelchenko.cinema.exception.LowBalanceException;
import ua.omelchenko.cinema.repository.TicketRepository;
import ua.omelchenko.cinema.service.TicketService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Log4j2
@Service
public class TicketServiceImpl implements TicketService {

    final TicketRepository ticketRepository;

    private final UserServiceImpl userService;

    private final SessionServiceImpl sessionService;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, UserServiceImpl userService, SessionServiceImpl sessionService) {
        this.ticketRepository = ticketRepository;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public Map<Integer, Long> getPlaces(Session session) {
        Optional<List<Ticket>> optionalTicket = ticketRepository.findAllBySessionId(session);
        Map<Integer, Long> places = new HashMap<>();
        if (optionalTicket.isPresent()) {
            for (Ticket ticket : optionalTicket.get()) {
                places.put(ticket.getPlace(), ticket.getUserId().getId());
            }
        }
        return places;
    }

    @Override
    @Transactional
    public void addTickets(List<Integer> places, Long sessionId) throws LowBalanceException {
        Optional<User> userOptional = userService.getCurrentUser();
        Optional<Session> sessionOptional = sessionService.getSessionById(sessionId);
        if (userOptional.isPresent() && sessionOptional.isPresent()) {
            int numberOfPlaces = places.size();
            User user = userOptional.get();
            Session session = sessionOptional.get();
            BigDecimal price = session
                    .getFilm().getPrice()
                    .multiply(BigDecimal.valueOf(numberOfPlaces));
            if (user.getBalance().compareTo(price) >= 0) {
                List<Ticket> toSave = new ArrayList<>();
                for (int place : places) {
                    Ticket ticket = new Ticket();
                    ticket.setPlace(place);
                    ticket.setUserId(user);
                    ticket.setSessionId(session);
                    toSave.add(ticket);
                }
                ticketRepository.saveAll(toSave);
                userService.updateBalance(price.negate());
                sessionService.updateNumberOfTickets(numberOfPlaces + session.getNumberOfTickets(), session);
            } else {
                throw new LowBalanceException();
            }
        }
    }

    @Override
    public Optional<List<Ticket>> getTicketsBySessionId(Session session) {
        return ticketRepository.findAllBySessionId(session);
    }

    @Override
    @Transactional
    public void removeTicketsBySession(Session session) {
        Optional<List<Ticket>> optionalTickets = getTicketsBySessionId(session);
        if (optionalTickets.isPresent()) {
            List<Ticket> tickets = optionalTickets.get();
            for (Ticket ticket : tickets) {
                userService.updateBalance(session.getFilm().getPrice(), ticket.getUserId());
                ticketRepository.delete(ticket);
            }
        }
    }
}
