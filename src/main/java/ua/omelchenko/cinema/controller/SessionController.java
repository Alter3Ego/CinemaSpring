package ua.omelchenko.cinema.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.omelchenko.cinema.entity.Session;
import ua.omelchenko.cinema.exception.LowBalanceException;
import ua.omelchenko.cinema.service.impl.SessionServiceImpl;
import ua.omelchenko.cinema.service.impl.TicketServiceImpl;
import ua.omelchenko.cinema.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
public class SessionController {
    private final SessionServiceImpl sessionService;
    private final TicketServiceImpl ticketService;
    private final UserServiceImpl userService;

    public SessionController(SessionServiceImpl sessionService, TicketServiceImpl ticketService, UserServiceImpl userService) {
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
    public String buyTickets(@PathVariable("id") long id,
                             @RequestParam(value = "tickets", required=false) List<Integer> tickets,
                             RedirectAttributes redirectAttributes) {
        log.warn(tickets);
        if (userService.isAuthentication()) {
            if (tickets != null && tickets.size() != 0) {
                try {
                    ticketService.addTickets(tickets, id);
                } catch (LowBalanceException e) {
                    redirectAttributes.addAttribute("errorBalance", true);
                } catch (DataIntegrityViolationException ex) {
                    log.error(ex);
                    redirectAttributes.addAttribute("operationError", true);
                }
            }
        } else {
            redirectAttributes.addAttribute("errorLogInSession", true);
        }
        return "redirect:/session/" + id;
    }
}
