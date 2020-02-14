package eu.treative.flightsintospace.repository;

import eu.treative.flightsintospace.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightCode(String code);
}
