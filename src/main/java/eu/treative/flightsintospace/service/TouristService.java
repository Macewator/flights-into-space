package eu.treative.flightsintospace.service;

import eu.treative.flightsintospace.model.Tourist;
import eu.treative.flightsintospace.repository.TouristRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TouristService {

    private TouristRepository touristRepository;

    public List<Tourist> gtaAllTourists() {
        List<Tourist> tourists = touristRepository.findAll();
        if (tourists == null || tourists.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no tourists found");
        }
        return tourists;
    }

    public Tourist getTouristById(Long id) {
        return touristRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "The given tourist does not exist"));
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

    public Tourist updateTourist(Tourist tourist){
        touristRepository.findByPesel(tourist.getPesel())
                .ifPresent(asset->
                {
                    if(!asset.getId().equals(tourist.getId())){
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "the pesel number already exists");
                    }
                });
        return touristRepository.save(tourist);
    }

    public void removeTourist(Tourist id) {
        touristRepository.delete(id);
    }
}
