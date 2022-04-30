package ua.omelchenko.cinema.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.omelchenko.cinema.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    boolean isAuthentication();

    Optional<User> getCurrentUser();

    boolean updateBalance(BigDecimal sum);

    boolean updateBalance(BigDecimal sum, User user);

    List<User> allUsers();

    boolean saveUser(User user);

    boolean deleteUser(Long userId);

}
