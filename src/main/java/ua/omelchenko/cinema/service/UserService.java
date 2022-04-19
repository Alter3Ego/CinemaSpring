package ua.omelchenko.cinema.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.omelchenko.cinema.entity.Role;
import ua.omelchenko.cinema.entity.User;
import ua.omelchenko.cinema.jdbc.repository.RoleRepository;
import ua.omelchenko.cinema.jdbc.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Log4j2
@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    final
    UserRepository userRepository;
    final
    RoleRepository roleRepository;
    final
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String REGEX_SUM = "\\d{1,8}";

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
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

    public Optional<User> getCurrentUser() {
        User authenticationUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(authenticationUser.getId());
    }
    @Transactional
    public boolean updateBalance(String sum){
        if (sum == null || !sum.matches(REGEX_SUM) || getCurrentUser().isEmpty()) {
            return false;
        } else {
            User currentUser = getCurrentUser().get();
            userRepository.updateBalanceBySum(new BigDecimal(sum), currentUser);
           return true;
        }
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

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

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

   /* public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }*/
}
