package ua.omelchenko.cinema.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import ua.omelchenko.cinema.entity.Film;
import ua.omelchenko.cinema.entity.Session;
import ua.omelchenko.cinema.repository.SessionRepository;
import ua.omelchenko.cinema.service.impl.SessionServiceImpl;
import ua.omelchenko.cinema.service.impl.TicketServiceImpl;

import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {


    @InjectMocks
    SessionServiceImpl sessionService;
    @Mock
    TicketServiceImpl ticketService;
    @Mock
    ObjectFactory<HttpSession> httpSessionFactory;
    @Mock
    SessionRepository sessionRepository;
    @Mock
    HttpSession httpSession;

    @Captor
    ArgumentCaptor<Integer> numberOfPageCaptor;

    @Captor
    ArgumentCaptor<Pageable> pageableCaptor;

    static final Integer HALL_OVER_CAPACITY = 50;
    static final Integer NUMBERS_OF_CELLS = 4;
    static final Integer HALL_CAPACITY = 20;

    @ParameterizedTest
    @MethodSource("generateData")
    void getPagingSessionsTest(Integer page, String sort, Boolean limitPlaces,
                               Integer expectedPage, String expectedSort, int expectedNumberOfPage) {


        ReflectionTestUtils.setField(sessionService, "HALL_CAPACITY", HALL_CAPACITY);
        ReflectionTestUtils.setField(sessionService, "HALL_OVER_CAPACITY", HALL_OVER_CAPACITY);
        ReflectionTestUtils.setField(sessionService, "NUMBERS_OF_CELLS", NUMBERS_OF_CELLS);

        when(httpSessionFactory.getObject()).thenReturn(httpSession);
        Map<String, Object> map = new HashMap<>();
        lenient().doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            Object value = invocation.getArgument(1);
            map.put(key, value);
            return null;
        }).when(httpSession).setAttribute(anyString(), any());
        when(httpSession.getAttribute(anyString())).thenAnswer(i -> map.get(i.getArguments()[0]));

        sessionService.getPagingSessions(page, sort, limitPlaces);
        Mockito.verify(sessionRepository, times(1)).
                findAllByNumberOfTicketsIsLessThan(numberOfPageCaptor.capture(), pageableCaptor.capture());
        assertEquals(expectedNumberOfPage, numberOfPageCaptor.getValue());
        assertEquals(NUMBERS_OF_CELLS, pageableCaptor.getValue().getPageSize());
        assertEquals(expectedPage, pageableCaptor.getValue().getPageNumber());
        assertEquals(expectedSort, pageableCaptor.getValue().getSort().toString());
    }

    static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(6, "name", false, 6, "film.title: ASC", HALL_OVER_CAPACITY),
                Arguments.of(7, "dateTime", true, 7, "dateTime: ASC", HALL_CAPACITY),
                Arguments.of(5, null, false, 5, "dateTime: ASC", HALL_OVER_CAPACITY),
                Arguments.of(9, "places", false, 9, "numberOfTickets: ASC", HALL_OVER_CAPACITY),
                Arguments.of(null, "dateTime", null, 0, "dateTime: ASC", HALL_OVER_CAPACITY),
                Arguments.of(null, null, null, 0, "dateTime: ASC", HALL_OVER_CAPACITY)
        );
    }

    private Session createSessionForTest(long sessionId) {
        Film film = new Film(2L, "Mad Max: Fury Road", 2015,
                new BigDecimal(400), "Action", "George Miller");
        Date date = new Date();
        return new Session(sessionId, date, film, 0);
    }

    @Test
    void getSessionByIdTest() {
        long id = 3;
        Session session = createSessionForTest(id);

        Optional<Session> expectSession = Optional.of(session);

        when(sessionRepository.findById(id)).thenReturn(expectSession);

        Optional<Session> actualSession = sessionService.getSessionById(id);
        assertEquals(actualSession, expectSession);
    }


}