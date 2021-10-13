package int222.project.repositories;

import int222.project.models.Place;
import int222.project.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import int222.project.models.TagType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TagTypeRepository extends JpaRepository<TagType, Integer> {

    @Transactional
    @Modifying
    @Query("SELECT tt.tag FROM TagType tt WHERE tt.typeId = 1")
    public List<Tag> listProvinceTag();

    @Transactional
    @Modifying
    @Query("SELECT tt.tag FROM TagType tt WHERE NOT tt.typeId = 1")
    public List<Tag> listEtcTag();
}
