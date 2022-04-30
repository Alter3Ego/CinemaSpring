package ua.omelchenko.cinema.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date dateTime;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
    @ColumnDefault("0")
    private Integer numberOfTickets;

    public Session() {
    }

    public Session(Long id, Date dateTime, Film film, Integer numberOfTickets) {
        this.id = id;
        this.dateTime = dateTime;
        this.film = film;
        this.numberOfTickets = numberOfTickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(dateTime, session.dateTime) && Objects.equals(film, session.film) && Objects.equals(numberOfTickets, session.numberOfTickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, film, numberOfTickets);
    }
}
