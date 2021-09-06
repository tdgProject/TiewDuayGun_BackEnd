package int222.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import int222.project.models.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

}
