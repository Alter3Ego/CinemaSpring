package ua.omelchenko.cinema.service.impl;

import org.springframework.stereotype.Service;
import ua.omelchenko.cinema.entity.Film;
import ua.omelchenko.cinema.repository.FilmRepository;
import ua.omelchenko.cinema.service.FilmService;

import java.util.List;
import java.util.Optional;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Optional<List<Film>> getAllFilms() {
        return filmRepository.findAllByOrderByTitle();
    }
}
