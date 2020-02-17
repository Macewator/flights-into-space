package eu.treative.flightsintospace.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_flight")
    private Long id;
    private LocalDateTime flightStart;
    private LocalDateTime flightEnd;
    private Integer numberOfSeats;
    private Integer numberOfTourist;
    private String ticketPrice;
    private String flightCode;
    @ManyToMany(mappedBy = "flights")
    @JsonIgnoreProperties("flights")
    @OrderBy("birthDate")
    List<Tourist> tourists = new ArrayList<>();
}
