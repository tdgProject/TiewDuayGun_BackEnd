package int222.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import int222.project.models.Hotel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface HotelRepository extends JpaRepository<Hotel, Integer> {

}
