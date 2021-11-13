package int222.project.repositories;

import int222.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import int222.project.models.Review;
import int222.project.models.ReviewPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, ReviewPK> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Review r WHERE r.user.userId = :id")
    void deleteByUserId(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("SELECT r FROM Review r WHERE r.place.placeId = :id")
    public List<Review> findAllByPlaceId(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("SELECT r FROM Review r WHERE r.user.userId = :id")
    public List<Review> findAllByUserId(@Param("id") Integer id);
}
