package int222.project.repositories;

import int222.project.models.TagType;
import org.springframework.data.jpa.repository.JpaRepository;
import int222.project.models.Tag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {

}
