package eu.treative.flightsintospace.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_flight")
    private Long id;
    private LocalDateTime flightStart;
    private LocalDateTime flightEnd;
    private Integer numberOfSeats;
    private Integer numberOfTourist;
    private String ticketPrice;
    private String flightCode;
    @ManyToMany(mappedBy = "flights")
    @OrderBy("birthDate")
    Set<Tourist> tourists = new HashSet<>();
}
