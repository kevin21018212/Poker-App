package com.example.poker.PlayerStats;

import com.example.poker.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class StatsService {

    //List of all Stats
    @Autowired
    private StatsRepository statsRepository;

    //Logic to get stats by id
    public Stats getStatsById(Long id) {
        return statsRepository.findById(id).orElse(null);
    }

    //Logic to create stats
    public void createStats(Stats stats) {
        statsRepository.save(stats);
    }

    //Logic to update stats
    public Stats updateStats(Long id, Stats request) {
        Optional<Stats> optionalStats = statsRepository.findById(id);
        if (optionalStats.isPresent()) {
            Stats stats = optionalStats.get();
            stats.setGamesPlayed(request.getGamesPlayed());
            stats.setWins(request.getWins());
            stats.setLosses(request.getLosses());
            try {
                return statsRepository.save(stats);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    //Logic to delete stats
    public void deleteStats(Long id) {
        statsRepository.deleteById(id);
    }

    //Logic to wins losses for a user
    public void incrementWins(User user) {
        Stats stats = user.getStats();

        if (stats != null) {
            int currentWins = stats.getWins();
            stats.setWins(currentWins + 1);

            try {
                statsRepository.save(stats);
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to increment wins", e);
            }
        } else {
            throw new RuntimeException("Cannot increment wins for a user with null stats");
        }
    }

    //Logic to update losses for a user
    public void incrementLosses(User user) {
        Stats stats = user.getStats();

        if (stats != null) {
            int currentLosses = stats.getLosses();
            stats.setLosses(currentLosses + 1);

            try {
                statsRepository.save(stats);
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to increment losses", e);
            }
        } else {
            throw new RuntimeException("Cannot increment losses for a user with null stats");
        }
    }


}
