package int222.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import int222.project.models.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

}
