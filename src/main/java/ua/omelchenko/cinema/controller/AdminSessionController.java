package ua.omelchenko.cinema.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.omelchenko.cinema.entity.Film;
import ua.omelchenko.cinema.entity.Session;
import ua.omelchenko.cinema.service.impl.FilmServiceImpl;
import ua.omelchenko.cinema.service.impl.SessionServiceImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
public class AdminSessionController {
    private final SessionServiceImpl sessionService;
    private final FilmServiceImpl filmService;

    public AdminSessionController(SessionServiceImpl sessionService, FilmServiceImpl filmService) {
        this.sessionService = sessionService;
        this.filmService = filmService;
    }

    @GetMapping("/admin/session")
    public String addSessionPage(
            @RequestParam(required = false, value = "successfulAdd") Boolean successfulAdd,
            @RequestParam(required = false, value = "errorDB") Boolean errorDB,
            Model model
    ) {
        model.addAttribute("successfulAdd", successfulAdd);
        model.addAttribute("errorDB", errorDB);

        model.addAttribute("session", new Session());
        Optional<List<Film>> films = filmService.getAllFilms();
        films.ifPresent(filmList -> model.addAttribute("films", filmList));

        return "admin";
    }

    @PostMapping("/admin/session")
    public String createNewSession(@ModelAttribute("session")
                                   @Valid Session session, BindingResult bindingResult,
                                   final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors() && sessionService.createSession(session)) {
            redirectAttributes.addAttribute("successfulAdd", true);
        } else {
            redirectAttributes.addAttribute("errorDB", true);
        }
        return "redirect:/admin/session";
    }

    @DeleteMapping("/admin/session/delete")
    public String deleteSession(@ModelAttribute("session") Long sessionId) {
        sessionService.removeSession(sessionId);
        return "redirect:/";
    }
}