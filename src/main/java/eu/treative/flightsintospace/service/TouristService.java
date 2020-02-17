package eu.treative.flightsintospace.service;

import eu.treative.flightsintospace.model.Flight;
import eu.treative.flightsintospace.model.Tourist;
import eu.treative.flightsintospace.repository.TouristRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TouristService {

    private TouristRepository touristRepository;

    public List<Tourist> gtaAllTourists(String lastName) {
        List<Tourist> tourists;
        if (lastName != null && !lastName.isEmpty()) {
            tourists = touristRepository.findAllByLastName(lastName);
        } else {
            tourists = touristRepository.findAll();
        }
        if (tourists == null || tourists.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no tourists found");
        }
        return tourists;
    }

    public Tourist getTouristById(Long id) {
        return touristRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "The given tourist does not exist"));
    }

    public List<Flight> getTouristFlights(Long id) {
        return getTouristById(id).getFlights();
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

    public Tourist updateTourist(Tourist tourist) {
        touristRepository.findByPesel(tourist.getPesel())
                .ifPresent(asset ->
                {
                    if (!asset.getId().equals(tourist.getId())) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "the pesel number already exists");
                    }
                });
        return touristRepository.save(tourist);
    }

    public void removeTourist(Long id) {
       Tourist tourist = getTouristById(id);
       tourist.getFlights().clear();
       touristRepository.delete(tourist);
    }

    public List<Flight> removeTouristFlight(Long touristId, Long flightId) {
        Tourist deletedTourist = getTouristById(touristId);
        return deletedTourist.getFlights().stream()
                .dropWhile(flight -> flight.getId().equals(flightId))
                .collect(Collectors.toList());
    }
}
