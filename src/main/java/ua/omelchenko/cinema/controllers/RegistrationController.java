package ua.omelchenko.cinema.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.omelchenko.cinema.entity.User;
import ua.omelchenko.cinema.service.UserService;

import javax.validation.Valid;

@Log4j2
@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            if (!userService.saveUser(user)) {
                model.addAttribute("emailError", true);
                return "registration";
            }
        }
        return "redirect:login";
    }
}
