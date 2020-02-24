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

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TouristService {

    private TouristRepository touristRepository;
    private FlightRepository flightRepository;

    public Page<Tourist> gtaAllTourists(String lastName, Pageable pageable) {
        Page<Tourist> tourists;
        if (lastName != null && !lastName.isEmpty()) {
            tourists = touristRepository.findAllByLastName(lastName, pageable);
        } else {
            tourists = touristRepository.findAll(pageable);
        }
        if (tourists.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no tourists found");
        }
        return tourists;
    }

    public List<Tourist> getAllAvailableTouristsForFlight(Long id, Integer page) {
        List<Tourist> tourists = new ArrayList<>();
        for (Tourist tourist : touristRepository.findAll()) {
            boolean availableTourist = tourist.getFlights().stream().anyMatch(flight -> flight.getId().equals(id));
            if (!availableTourist) {
                tourists.add(tourist);
            }
        }
        if (tourists.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no available tourist found");
        }
        int idxStart = 5 * (page - 1);
        return tourists.subList(idxStart, idxStart + 5);
    }

    public Tourist getTouristById(Long id) {
        return touristRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "The given tourist does not exist"));
    }

    public List<Flight> getTouristFlights(Long id, Integer page) {
        List<Flight> flights = getTouristById(id).getFlights();
        int idxStart = 5 * (page - 1);
        return flights.subList(idxStart, idxStart + 5);
    }

    public Tourist saveNewTourist(Tourist tourist) {
        touristRepository.findByPesel(tourist.getPesel())
                .ifPresent(asset ->
                {
                    if (asset.getPesel().equals(tourist.getPesel())) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "the given tourist is already in the database");
                    }
                });
        return touristRepository.save(tourist);
    }

    public Tourist manageTouristsFlight(Tourist tourist, Long idf, String action) {
        touristRepository.findByPesel(tourist.getPesel())
                .ifPresent(asset ->
                {
                    if (!asset.getId().equals(tourist.getId())) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "the pesel number already exists");
                    }
                });
        Flight flight = flightRepository.findById(idf)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The given flight does not exist"));
        return updateTourist(tourist, action, flight);
    }

    private Tourist updateTourist(Tourist tourist, String action, Flight flight) {
        Tourist updatedTourist = getTouristById(tourist.getId());
        if ("remove".equals(action)) {
            updatedTourist.removeFlight(flight);
            return touristRepository.save(updatedTourist);
        } else {
            updatedTourist.addFlight(flight);
            return touristRepository.save(updatedTourist);
        }
    }

    public void removeTourist(Long id) {
        Tourist tourist = getTouristById(id);
        List<Flight> flights = tourist.getFlights();
        for (int i = 0; i < flights.size(); i++) {
            Flight flight = flights.get(i);
            tourist.removeFlight(flight);
        }
        touristRepository.delete(tourist);
    }
}
