package int222.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import int222.project.models.TagPlace;
import int222.project.models.TagPlacePK;

public interface TagPlaceRepository extends JpaRepository<TagPlace, TagPlacePK> {

}
