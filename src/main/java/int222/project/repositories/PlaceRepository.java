package int222.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import int222.project.models.Place;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

    @Transactional
    @Modifying
    @Query("SELECT p FROM Place p WHERE p.placeName like %:name%")
    public List<Place> listPLaceByName(@Param("name") String name);
}
