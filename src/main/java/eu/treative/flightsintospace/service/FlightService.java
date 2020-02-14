package eu.treative.flightsintospace.service;

import eu.treative.flightsintospace.model.Flight;
import eu.treative.flightsintospace.model.Tourist;
import eu.treative.flightsintospace.repository.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class FlightService {

    private FlightRepository flightRepository;

    public List<Flight> gtaAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        if (flights == null || flights.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no flights found");
        }
        return flights;
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "The given flight does not exist"));
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

    public Flight updateFlight(Flight flight){
        flightRepository.findByFlightCode(flight.getFlightCode())
                .ifPresent(asset->
                {
                    if(!asset.getId().equals(flight.getId())){
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "the code number already exists");
                    }
                });
        return flightRepository.save(flight);
    }

    public void removeFlight(Flight id) {
        flightRepository.delete(id);
    }
}
