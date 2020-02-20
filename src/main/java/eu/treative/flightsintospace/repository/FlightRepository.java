package eu.treative.flightsintospace.repository;

import eu.treative.flightsintospace.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightCode(String code);

    List<Flight> findAllByFlightStartGreaterThanEqual(LocalDateTime start);

    List<Flight> findAllByFlightEndGreaterThanEqual(LocalDateTime end);

    List<Flight> findAllByNumberOfSeats(Integer seats);

    List<Flight> findAllByNumberOfTourist(Integer tourists);

    List<Flight> findAllByTicketPrice(String price);
}
