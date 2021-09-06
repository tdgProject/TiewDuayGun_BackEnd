package int222.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import int222.project.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
