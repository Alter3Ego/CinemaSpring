package ua.omelchenko.cinema.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.omelchenko.cinema.entity.User;
import ua.omelchenko.cinema.service.UserService;

import java.math.BigDecimal;
import java.util.Optional;

@Log4j2
@Controller
public class UserController {

    @Autowired
    private UserService userService;

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
    public String updateBalance(@ModelAttribute("updateBalance") BigDecimal sum, Model model) {
        if (sum.compareTo(BigDecimal.valueOf(0)) > 0 && userService.updateBalance(sum)) {
            return "redirect:/userPage";
        }
        model.addAttribute("updateError", "{user.error.replenish}");
        return userPage(model);

    }
}