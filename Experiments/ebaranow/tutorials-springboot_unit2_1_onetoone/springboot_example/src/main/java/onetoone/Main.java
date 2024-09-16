package onetoone;

import onetoone.PlayerStats.Stats;
import onetoone.PlayerStats.StatsRepository;
import onetoone.Results.GameResult;
import onetoone.Results.GameResultRepository;
import onetoone.Session.GameSession;
import onetoone.Session.GameSessionRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner initData(
            UserRepository userRepository,
            GameSessionRepository gameSessionRepository,
            GameResultRepository gameResultRepository,
            StatsRepository statsRepository) {
        return args -> {

            User user1 = new User();
            user1.setUsername("John");
            user1.setEmail("john@somemail.com");
            user1.setPassword("password");
            user1.setChips(1000);

            User user2 = new User();
            user2.setUsername("Jane");
            user2.setEmail("jane@somemail.com");
            user2.setPassword("password");
            user2.setChips(1500);

            User user3 = new User();
            user3.setUsername("Justin");
            user3.setEmail("justin@somemail.com");
            user3.setPassword("password");
            user3.setChips(800);

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);


            GameSession session1 = new GameSession();
            session1.setUser(user1);
            session1.setStartTime(new Date());
            session1.setEndTime(new Date());

            GameSession session2 = new GameSession();
            session2.setUser(user2);
            session2.setStartTime(new Date());
            session2.setEndTime(new Date());

            gameSessionRepository.save(session1);
            gameSessionRepository.save(session2);


            GameResult result1 = new GameResult();
            result1.setGameSession(session1);
            result1.setGameType("Texas Hold-em");
            result1.setWinnerUser(user1);
            result1.setLoserUser(user2);

            GameResult result2 = new GameResult();
            result2.setGameSession(session2);
            result2.setGameType("Omaha");
            result2.setWinnerUser(user2);
            result2.setLoserUser(user1);

            gameResultRepository.save(result1);
            gameResultRepository.save(result2);


            Stats stats1 = new Stats();
            stats1.setUser(user1);
            stats1.setGamesPlayed(10);
            stats1.setWins(4);
            stats1.setLosses(6);
            stats1.setTotalChipsWon(5000);
            stats1.setTotalChipsLost(3000);

            Stats stats2 = new Stats();
            stats2.setUser(user2);
            stats2.setGamesPlayed(15);
            stats2.setWins(8);
            stats2.setLosses(7);
            stats2.setTotalChipsWon(7500);
            stats2.setTotalChipsLost(6000);

            statsRepository.save(stats1);
            statsRepository.save(stats2);
        };
    }
}
