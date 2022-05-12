package ua.omelchenko.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.omelchenko.cinema.entity.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}