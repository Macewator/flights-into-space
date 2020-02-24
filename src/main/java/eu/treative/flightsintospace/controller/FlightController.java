package eu.treative.flightsintospace.controller;

import eu.treative.flightsintospace.model.Flight;
import eu.treative.flightsintospace.model.Tourist;
import eu.treative.flightsintospace.service.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<List<Flight>> getAllFlights(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false) String category,
                                                      @PageableDefault(sort = "flightStart", direction = Sort.Direction.ASC)
                                                              Pageable pageable) {
        Page<Flight> flights = flightService.gtaAllFlights(keyword, category, pageable);
        List<Flight> flightList = flights.getContent();
        return ResponseEntity.ok(flightList);
    }

    @GetMapping(path = "/tourists/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Flight>> getAllAvailableFlightsForTourist(@PathVariable Long id,
                                                                         @RequestParam Integer page) {
        List<Flight> flights = flightService.getAllAvailableFlightsForTourist(id, page);
        return ResponseEntity.ok(flights);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        return ResponseEntity.ok(flight);
    }

    @GetMapping(path = "/{id}/tourists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tourist>> getFlightTourists(@PathVariable Long id,
                                                           @RequestParam Integer page) {
        return ResponseEntity.ok(flightService.getFlightTourists(id, page));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flight> saveNewFlight(@RequestBody Flight flight) {
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

    @PutMapping(path = "/{idF}/tourists/{idT}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flight> manageFlightsTourists(@RequestParam String action, @PathVariable Long idF, @PathVariable Long idT, @RequestBody Flight flight) {
        if (!idF.equals(flight.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid id of the tourist sent");
        }
        Flight updatedFlight = flightService.manageFlightsTourists(flight, idT, action);
        return ResponseEntity.ok(updatedFlight);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> removeFlight(@PathVariable Long id) {
        flightService.removeFlight(id);
        return ResponseEntity.ok().build();
    }
}
