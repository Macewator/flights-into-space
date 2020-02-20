package eu.treative.flightsintospace.controller;

import eu.treative.flightsintospace.model.Flight;
import eu.treative.flightsintospace.model.Tourist;
import eu.treative.flightsintospace.service.TouristService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tourists")
public class TouristController {

    private TouristService touristService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tourist>> getAllTourist(@RequestParam(required = false) String lastName) {
        List<Tourist> tourists = touristService.gtaAllTourists(lastName);
        return ResponseEntity.ok(tourists);
    }

    @GetMapping(path = "/flights/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tourist>> getAllAvailableTouristsForFlight(@PathVariable Long id) {
        List<Tourist> tourists = touristService.getAllAvailableTouristsForFlight(id);
        return ResponseEntity.ok(tourists);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tourist> getTouristById(@PathVariable Long id) {
        Tourist tourist = touristService.getTouristById(id);
        return ResponseEntity.ok(tourist);
    }

    @GetMapping(path = "/{id}/flights", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Flight>> getTouristFlights(@PathVariable Long id) {
        return ResponseEntity.ok(touristService.getTouristFlights(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tourist> saveNewTourist(@RequestBody Tourist tourist) {
        if (tourist.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "added tourist cannot have id set");
        }
        Tourist savedTourist = touristService.saveNewTourist(tourist);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTourist.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedTourist);
    }

    @PutMapping(path = "/{idT}/flights/{idF}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tourist> manageTouristFlights(@RequestParam String action, @PathVariable Long idT, @PathVariable Long idF, @RequestBody Tourist tourist) {
        if (!idT.equals(tourist.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid id of the tourist sent");
        }
        Tourist updatedTourist = touristService.manageTouristsFlight(tourist, idF, action);
        return ResponseEntity.ok(updatedTourist);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> removeTourist(@PathVariable Long id) {
        touristService.removeTourist(id);
        return ResponseEntity.ok().build();
    }
}
