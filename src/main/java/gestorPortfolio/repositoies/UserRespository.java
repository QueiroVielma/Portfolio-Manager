package gestorPortfolio.repositoies;

import gestorPortfolio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User, Long> {
}
