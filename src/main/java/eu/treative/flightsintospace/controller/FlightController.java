package eu.treative.flightsintospace.controller;

import eu.treative.flightsintospace.model.Flight;
import eu.treative.flightsintospace.model.Tourist;
import eu.treative.flightsintospace.service.FlightService;
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
@RequestMapping("/api/flights")
public class FlightController {

    private FlightService flightService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.gtaAllFlights();
        return ResponseEntity.ok(flights);
    }

    @GetMapping(path = "/tourists/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Flight>> getAllAvailableFlightsForTourist(@PathVariable Long id) {
        List<Flight> flights = flightService.getAllAvailableFlightsForTourist(id);
        return ResponseEntity.ok(flights);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        return ResponseEntity.ok(flight);
    }

    @GetMapping(path = "/{id}/tourists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tourist>> getFlightTourists(@PathVariable Long id){
        return ResponseEntity.ok(flightService.getFlightTourists(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveNewFlight(@RequestBody Flight flight) {
        if (flight.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "added flight cannot have id set");
        }
        Flight savedFlight = flightService.saveNewFlight(flight);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedFlight.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedFlight);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flight> updateFlights(@PathVariable Long id, @RequestBody Flight flight){
        if(!id.equals(flight.getId())){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid id of the tourist sent");
        }
        Flight updatedFlight = flightService.updateFlight(flight);
        return ResponseEntity.ok(updatedFlight);
    }

    /*@DeleteMapping("/{flightId}/flights/{touristId}")
    public ResponseEntity<List<Tourist>> removeFlightTourist(@PathVariable Long flightId, @PathVariable Long touristId){
        List<Tourist> flights = flightService.removeFlightTourist(flightId, touristId);
        return ResponseEntity.ok(flights);
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFlight(@PathVariable Long id){
        flightService.removeFlight(id);
        return ResponseEntity.ok().build();
    }
}
