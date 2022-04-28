package ua.omelchenko.cinema.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.omelchenko.cinema.entity.Session;
import ua.omelchenko.cinema.exception.LowBalanceException;
import ua.omelchenko.cinema.service.SessionService;
import ua.omelchenko.cinema.service.TicketService;
import ua.omelchenko.cinema.service.UserService;

import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
public class SessionController {
    private final SessionService sessionService;
    private final TicketService ticketService;
    private final UserService userService;

    public SessionController(SessionService sessionService, TicketService ticketService, UserService userService) {
        this.sessionService = sessionService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @GetMapping("/session/{id}")
    public String sessionPage(@PathVariable("id") long id, Model model) {
        Optional<Session> optionalSession = sessionService.getSessionById(id);

        if (optionalSession.isPresent()) {
            Session session = optionalSession.get();
            model.addAttribute("currentSession", session);
            model.addAttribute("places", ticketService.getPlaces(session));

            if (userService.isAuthentication()) {
                model.addAttribute("isAuthentication", userService.getCurrentUser().get().getId());
            } else {
                model.addAttribute("isAuthentication", null);
            }
            return "session";
        }
        return "error404";
    }

    @PostMapping("/session/{id}")
    public String buyTickets(@PathVariable("id") long id, @RequestBody List<Integer> tickets, Model model) {
        if (userService.isAuthentication()) {
            if (tickets.size() != 0) {
                try {
                    ticketService.addTickets(tickets, id);
                } catch (LowBalanceException e) {
                    model.addAttribute("errorBalance", true);
                } catch (DataIntegrityViolationException ex) {
                    log.error(ex);
                    model.addAttribute("operationError", true);
                }
            }
        } else {
            model.addAttribute("errorLogInSession", true);
        }
        return sessionPage(id, model);
    }
}
