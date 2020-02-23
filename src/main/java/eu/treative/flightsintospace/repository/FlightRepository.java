package eu.treative.flightsintospace.repository;

import eu.treative.flightsintospace.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightCode(String code);

    Page<Flight> findAllByFlightStartGreaterThanEqual(LocalDateTime start, Pageable pageable);

    Page<Flight> findAllByFlightEndGreaterThanEqual(LocalDateTime end, Pageable pageable);

    Page<Flight> findAllByNumberOfSeats(Integer seats, Pageable pageable);

    Page<Flight> findAllByNumberOfTourist(Integer tourists, Pageable pageable);

    Page<Flight> findAllByTicketPrice(String price, Pageable pageable);
}
