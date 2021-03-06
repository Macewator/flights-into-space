package eu.treative.flightsintospace.service;

import eu.treative.flightsintospace.model.Flight;
import eu.treative.flightsintospace.model.Tourist;
import eu.treative.flightsintospace.repository.FlightRepository;
import eu.treative.flightsintospace.repository.TouristRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class FlightService {

    private FlightRepository flightRepository;
    private TouristRepository touristRepository;

    public Page<Flight> gtaAllFlights(String keyword, String category, Pageable page) {
        Page<Flight> flights = getFlights(keyword, category, page);
        if (flights.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no flights found");
        }
        return flights;
    }

    private Page<Flight> getFlights(String keyword, String category, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            switch (category) {
                case "start":
                    DateTimeFormatter ftr1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime startDateTime = LocalDateTime.parse(keyword, ftr1);
                    return flightRepository.findAllByFlightStartGreaterThanEqual(startDateTime, pageable);
                case "end":
                    DateTimeFormatter ftr2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime ednDateTime = LocalDateTime.parse(keyword, ftr2);
                    return flightRepository.findAllByFlightEndGreaterThanEqual(ednDateTime, pageable);

                case "seats":
                    return flightRepository.findAllByNumberOfSeats(Integer.parseInt(keyword), pageable);

                case "tourists":
                    return flightRepository.findAllByNumberOfTourist(Integer.parseInt(keyword), pageable);

                case "price":
                    return flightRepository.findAllByTicketPrice(keyword, pageable);

            }
        }
        return flightRepository.findAll(pageable);
    }

    public List<Flight> getAllAvailableFlightsForTourist(Long id, Integer page) {
        List<Flight> flights = new ArrayList<>();
        for (Flight flight : flightRepository.findAll()) {
            boolean availableFlight = flight.getTourists().stream().anyMatch(tourist -> tourist.getId().equals(id));
            if (!availableFlight && flight.getNumberOfTourist() != 10) {
                flights.add(flight);
            }
        }
        if (flights.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no available flights found");
        }
        int idxStart = 5 * (page - 1);
        return flights.subList(idxStart, idxStart + 5);
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "The given flight does not exist"));
    }

    public List<Tourist> getFlightTourists(Long id, Integer page) {
        List<Tourist> tourists = getFlightById(id).getTourists();
        int idxStart = 5 * (page - 1);
        return tourists.subList(idxStart, idxStart + 5);
    }

    public Flight saveNewFlight(Flight flight) {
        flightRepository.findByFlightCode(flight.getFlightCode())
                .ifPresent(asset ->
                {
                    if (asset.getFlightCode().equals(flight.getFlightCode())) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "the given flight is already in the database");
                    }
                });
        return flightRepository.save(flight);
    }

    public Flight manageFlightsTourists(Flight flight, Long idT, String action) {
        flightRepository.findByFlightCode(flight.getFlightCode())
                .ifPresent(asset ->
                {
                    if (!asset.getId().equals(flight.getId())) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "the code number already exists");
                    }
                });
        Tourist tourist = touristRepository.findById(idT)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The given tourist does not exist"));
        return updateFlight(flight, action, tourist);
    }

    private Flight updateFlight(Flight flight, String action, Tourist tourist) {
        Flight updatedFlight = getFlightById(flight.getId());
        if ("remove".equals(action)) {
            tourist.removeFlight(updatedFlight);
            return flightRepository.save(updatedFlight);
        } else {
            if (flight.getNumberOfTourist().equals(10)) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Chosen flight is full");
            }
            tourist.addFlight(updatedFlight);
            return flightRepository.save(updatedFlight);
        }
    }

    public void removeFlight(Long id) {
        Flight flight = getFlightById(id);
        List<Tourist> tourists = flight.getTourists();
        for (int i = 0; i < tourists.size(); i++) {
            Tourist tourist = tourists.get(i);
            tourist.removeFlight(flight);
        }
        flightRepository.delete(flight);
    }
}
