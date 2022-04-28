package ua.omelchenko.cinema.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tickets",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"session_id", "place"},
                        name = "session_id_place_mask"
                )
        }
)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private Session sessionId;
    private int place;
}
