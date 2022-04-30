package ua.omelchenko.cinema.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.omelchenko.cinema.entity.Role;
import ua.omelchenko.cinema.entity.User;
import ua.omelchenko.cinema.jdbc.repository.RoleRepository;
import ua.omelchenko.cinema.jdbc.repository.UserRepository;
import ua.omelchenko.cinema.service.UserService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    final
    UserRepository userRepository;
    final
    RoleRepository roleRepository;
    final
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public boolean isAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();

    }

    @Override
    public Optional<User> getCurrentUser() {
        User authenticationUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(authenticationUser.getId());
    }

    @Override
    @Transactional
    public boolean updateBalance(BigDecimal sum) {
        Optional<User> currentUser = getCurrentUser();
        return currentUser.filter(user -> updateBalance(sum, user)).isPresent();
    }

    @Override
    @Transactional
    public boolean updateBalance(BigDecimal sum, User user) {
        int updateBalanceBySum = userRepository.updateBalanceBySum(sum, user);
        return updateBalanceBySum == 1;
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByEmail(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setBalance(new BigDecimal(0));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
