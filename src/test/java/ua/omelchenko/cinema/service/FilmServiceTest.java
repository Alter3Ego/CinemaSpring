package ua.omelchenko.cinema.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import ua.omelchenko.cinema.entity.Film;
import ua.omelchenko.cinema.jdbc.repository.FilmRepository;
import ua.omelchenko.cinema.service.impl.FilmServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @InjectMocks
    FilmServiceImpl filmService;
    @Mock
    FilmRepository filmRepository;

    @Test
    void getAllFilmsTest() {
        List<Film> films = new ArrayList<>();
        Film film = new Film(2L, "Mad Max: Fury Road", 2015,
                new BigDecimal(400), "Action", "George Miller");
        films.add(film);

        Optional<List<Film>> expectFilmList = Optional.of(films);

        when(filmRepository.findAllByOrderByTitle()).thenReturn(expectFilmList);

        Optional<List<Film>> actualFilmList = filmService.getAllFilms();
        assertEquals(actualFilmList, expectFilmList);
    }
}