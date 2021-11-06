package int222.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import int222.project.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByTelNumber(String tel);
}
