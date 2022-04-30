package ua.omelchenko.cinema.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1, max = 255)
    private String title;
    private Integer releaseYear;
    private BigDecimal price;
    @Size(min = 1, max = 50)
    private String genre;
    @Size(min = 1, max = 80)
    private String producer;

    public Film() {
    }

    public Film(Long id, String title, Integer releaseYear, BigDecimal price, String genre, String producer) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.price = price;
        this.genre = genre;
        this.producer = producer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(id, film.id) && Objects.equals(title, film.title) && Objects.equals(releaseYear, film.releaseYear) && Objects.equals(price, film.price) && Objects.equals(genre, film.genre) && Objects.equals(producer, film.producer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, releaseYear, price, genre, producer);
    }
}
