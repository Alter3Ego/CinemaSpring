package ua.omelchenko.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.omelchenko.cinema.entity.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {
    Optional<List<Film>> findAllByOrderByTitle();
}
