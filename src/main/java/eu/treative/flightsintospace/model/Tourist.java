package eu.treative.flightsintospace.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Tourist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JsonIgnoreProperties("tourists")
    @OrderBy("flightStart")
    @JoinTable(name = "tourist_flights",
            joinColumns = @JoinColumn(name = "tourist_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id"))
    private List<Flight> flights = new ArrayList<>();

    public Tourist() {
    }

    public Tourist(String firstName, String lastName, Gender gender, String country, String notes, LocalDate birthDate, String pesel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.country = country;
        this.notes = notes;
        this.birthDate = birthDate;
        this.pesel = pesel;
    }

    public void addFlight(Flight flight) {
        flight.setNumberOfTourist(flight.getNumberOfTourist() + 1);
        this.flights.add(flight);
        flight.getTourists().add(this);
    }

    public void removeFlight(Flight flight) {
        flight.setNumberOfTourist(flight.getNumberOfTourist() - 1);
        this.flights.remove(flight);
        flight.getTourists().remove(this);
    }
}
