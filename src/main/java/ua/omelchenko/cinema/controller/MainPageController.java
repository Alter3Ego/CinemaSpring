package ua.omelchenko.cinema.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.omelchenko.cinema.entity.Session;
import ua.omelchenko.cinema.service.impl.SessionServiceImpl;

import java.util.List;

@Log4j2
@Controller
public class MainPageController {

    private final SessionServiceImpl sessionService;

    public MainPageController(SessionServiceImpl sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/")
    public String userPage(@RequestParam(required = false, value = "page") Integer page,
                           @RequestParam(required = false, value = "sort") String sort,
                           @RequestParam(required = false, value = "limitPlaces") Boolean limitPlaces,
                           Model model) {
        Page<Session> sessionPage = sessionService.getPagingSessions(page, sort, limitPlaces);

        if (sessionPage != null) {
            int i = 1;
            List<Session> sessionList = sessionPage.getContent();
            for (Session session : sessionList) {
                model.addAttribute("mainCell" + i++, session);
            }
            int totalPages = sessionPage.getTotalPages();

            page = page == null ? 0 : page;

            if (page != 0) {
                model.addAttribute("previousPage", "page=" + (page - 1));
            }
            if (totalPages != 0 && totalPages != page + 1) {
                model.addAttribute("nextPage", "page=" + (page + 1));
            }
        }
        return "index";
    }
}
