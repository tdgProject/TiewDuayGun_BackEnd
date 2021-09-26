package int222.project.repositories;

import int222.project.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import int222.project.models.NearBy;
import int222.project.models.NearByPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NearByRepository extends JpaRepository<NearBy, NearByPK> {
    @Transactional
    @Modifying
    @Query("SELECT n FROM NearBy n WHERE n.place.placeId = :id")
    public List<NearBy> findAllByPlaceId(@Param("id") Integer id);
}
