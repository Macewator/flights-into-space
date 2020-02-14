package eu.treative.flightsintospace.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Tourist {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_tourist")
    private Long id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String country;
    private String notes;
    private LocalDate birthDate;
    private String pesel;
    @ManyToMany
    @OrderBy("flightStart")
    @JoinTable(name = "tourist_flights",
            joinColumns = @JoinColumn(name = "tourist_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id"))
    private List<Flight> flights;
}
