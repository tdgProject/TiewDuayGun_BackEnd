package int222.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import int222.project.models.NearBy;
import int222.project.models.NearByPK;

public interface NearByRepository extends JpaRepository<NearBy, NearByPK> {

}
