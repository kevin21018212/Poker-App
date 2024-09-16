package onetoone.PlayerStats;



import onetoone.PlayerStats.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface StatsRepository  extends JpaRepository<Stats, Long>{


}
