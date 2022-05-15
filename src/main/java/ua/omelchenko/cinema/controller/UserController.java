package ua.omelchenko.cinema.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.omelchenko.cinema.entity.User;
import ua.omelchenko.cinema.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.util.Optional;

@Log4j2
@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/userPage")
    public String userPage(Model model) {
        Optional<User> userOptional = userService.getCurrentUser();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user);
            return "userPage";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/userPage")
    public String updateBalance(@ModelAttribute("updateBalance") String value, Model model) {
        try {
            BigDecimal sum = new BigDecimal(value);
            if (sum.compareTo(BigDecimal.valueOf(0)) > 0 && userService.updateBalance(sum)) {
                return "redirect:/userPage";
            }
        } catch (NumberFormatException ex) {
            log.warn("Update balance error: " + ex);
        }
        model.addAttribute("updateError", true);
        return userPage(model);
    }
}