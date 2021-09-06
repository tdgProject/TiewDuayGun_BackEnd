package int222.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import int222.project.models.Place;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

}
