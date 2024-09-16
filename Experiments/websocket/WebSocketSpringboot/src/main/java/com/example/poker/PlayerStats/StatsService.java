package com.example.poker.PlayerStats;

import com.example.poker.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {

    @Autowired
    private StatsRepository statsRepository;

    public Stats getStatsById(Long id) {
        return statsRepository.findById(id).orElse(null);
    }

    public void createStats(Stats stats) {
        statsRepository.save(stats);
    }

    public Stats updateStats(Long id, Stats request) {
        Stats stats = statsRepository.findById(id).orElse(null);
        if (stats != null) {

            stats.setGamesPlayed(request.getGamesPlayed());
            stats.setWins(request.getWins());
            stats.setLosses(request.getLosses());
            return statsRepository.save(stats);
        }
        return null;
    }

    public void deleteStats(Long id) {
        statsRepository.deleteById(id);
    }

    public void incrementWins(User user) {
        Stats stats = user.getStats();
        if (stats != null) {
            int currentWins = stats.getWins();
            stats.setWins(currentWins + 1);
            statsRepository.save(stats);
        }
    }

    public void incrementLosses(User user) {
        Stats stats = user.getStats();
        if (stats != null) {
            int currentLosses = stats.getLosses();
            stats.setLosses(currentLosses + 1);
            statsRepository.save(stats);
        }
    }

}
