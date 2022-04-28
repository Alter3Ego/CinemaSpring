package ua.omelchenko.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.omelchenko.cinema.entity.Film;
import ua.omelchenko.cinema.jdbc.repository.FilmRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {

    final FilmRepository filmRepository;
    @Autowired
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Optional<List<Film>> getAllFilms(){
        return filmRepository.findAllByOrderByTitle();
    }




}
