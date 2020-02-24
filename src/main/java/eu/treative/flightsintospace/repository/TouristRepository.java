package eu.treative.flightsintospace.repository;

import eu.treative.flightsintospace.model.Tourist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TouristRepository extends JpaRepository<Tourist,Long> {

    Optional<Tourist> findByPesel(String pesel);
    Page<Tourist> findAllByLastName(String lastName, Pageable pageable);
}
