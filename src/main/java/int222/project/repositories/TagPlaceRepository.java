package int222.project.repositories;

import int222.project.models.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import int222.project.models.TagPlace;
import int222.project.models.TagPlacePK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TagPlaceRepository extends JpaRepository<TagPlace, TagPlacePK> {
    @Transactional
    @Modifying
    @Query("DELETE FROM TagPlace tp WHERE tp.place.placeId = :id")
    void deleteByPlaceId(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("SELECT tp.place FROM TagPlace tp WHERE tp.tag.tagId = :id")
    public List<Place> listPLaceByTagId(@Param("id") Integer id);

}
