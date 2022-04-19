package ua.omelchenko.cinema.jdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.omelchenko.cinema.entity.User;

import java.math.BigDecimal;
import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String username);
    @Modifying
    @Query(value = "UPDATE users SET balance = balance + ?  WHERE id = ?", nativeQuery = true)
    void updateBalanceBySum( BigDecimal sum, User user);

}
