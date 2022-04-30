package ua.omelchenko.cinema.service;

import ua.omelchenko.cinema.entity.Film;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    Optional<List<Film>> getAllFilms();
}
