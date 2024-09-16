package onetoone.Session;
import onetoone.PlayerStats.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {


}
