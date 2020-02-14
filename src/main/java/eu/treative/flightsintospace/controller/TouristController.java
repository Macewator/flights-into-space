package eu.treative.flightsintospace.controller;

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
@RequestMapping("/api/tourist")
public class TouristController {

    private TouristService touristService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tourist>> getAllTourist() {
        List<Tourist> tourists = touristService.gtaAllTourists();
        return ResponseEntity.ok(tourists);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tourist> getTouristById(@PathVariable Long id) {
        Tourist tourist = touristService.getTouristById(id);
        return ResponseEntity.ok(tourist);
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

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tourist> updateTourist(@PathVariable Long id, @RequestBody Tourist tourist){
        if(!id.equals(tourist.getId())){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid id of the tourist sent");
        }
        Tourist updatedTourist = touristService.updateTourist(tourist);
        return ResponseEntity.ok(updatedTourist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTourist(@PathVariable Long id){
        Tourist deletedTourist = touristService.getTouristById(id);
        touristService.removeTourist(deletedTourist);
        return ResponseEntity.ok().build();
    }
}
