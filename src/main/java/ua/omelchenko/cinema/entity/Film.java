package ua.omelchenko.cinema.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

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
}
