package int222.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import int222.project.models.Review;
import int222.project.models.ReviewPK;

public interface ReviewRepository extends JpaRepository<Review, ReviewPK> {

}
