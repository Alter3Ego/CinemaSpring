package ua.omelchenko.cinema.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.omelchenko.cinema.entity.Film;
import ua.omelchenko.cinema.entity.Session;
import ua.omelchenko.cinema.entity.User;
import ua.omelchenko.cinema.exception.LowBalanceException;
import ua.omelchenko.cinema.service.impl.SessionServiceImpl;
import ua.omelchenko.cinema.service.impl.TicketServiceImpl;
import ua.omelchenko.cinema.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @InjectMocks
    TicketServiceImpl ticketService;
    @Mock
    UserServiceImpl userService;
    @Mock
    SessionServiceImpl sessionService;

    @Test
    void addTicketsLowBalanceExceptionTest() {
        User user = new User(3L, "Ivan", "Petrovich", "test@.mail.com", "somePassword3", BigDecimal.valueOf(50));
        long sessionId = 2;
        Session session = createSessionForTest(sessionId);
        when(userService.getCurrentUser()).thenReturn(Optional.of(user));
        when(sessionService.getSessionById(anyLong())).thenReturn(Optional.of(session));

        LowBalanceException ex = assertThrows(LowBalanceException.class,
                () -> ticketService.addTickets(List.of(2), sessionId));

        assertNotNull(ex);
    }

    private Session createSessionForTest(long sessionId) {
        Film film = new Film(2L, "Mad Max: Fury Road", 2015,
                new BigDecimal(400), "Action", "George Miller");
        Date date = new Date();
        return new Session(sessionId, date, film, 0);
    }


}