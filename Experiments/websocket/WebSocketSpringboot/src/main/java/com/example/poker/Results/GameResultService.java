package com.example.poker.Results;

import com.example.poker.Users.User;
import com.example.poker.PlayerStats.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GameResultService {

    @Autowired
    private GameResultRepository gameResultRepository;

    @Autowired
    private StatsService statsService;

    public List<GameResult> getAllGameResults() {
        return gameResultRepository.findAll();
    }

    public Optional<GameResult> getGameResultById(Long id) {
        return gameResultRepository.findById(id);
    }

    public GameResult createGameResult(GameResult gameResult) {
        // Add logic to increment win and loss statistics
        User winner = gameResult.getWinnerUser();
        List<User> losers = gameResult.getLoserUsers();

        if (winner != null) {
            statsService.incrementWins(winner);
        }

        for (User loser : losers) {
            statsService.incrementLosses(loser);
        }

        return gameResultRepository.save(gameResult);
    }

    public GameResult updateGameResult(Long id, GameResult request) throws Exception {
        Optional<GameResult> optionalGameResult = gameResultRepository.findById(id);
        if (optionalGameResult.isPresent()) {
            GameResult gameResult = optionalGameResult.get();
            gameResult.setGameSession(request.getGameSession());
            gameResult.setWinnerUser(request.getWinnerUser());
            gameResult.setLoserUsers(request.getLoserUsers());
            User winner = gameResult.getWinnerUser();
            List<User> losers = gameResult.getLoserUsers();

            if (winner != null) {
                statsService.incrementWins(winner);
            }

            for (User loser : losers) {
                statsService.incrementLosses(loser);
            }

            return gameResultRepository.save(gameResult);
        } else {
            throw new Exception("Game result not found with ID: " + id);
        }
    }

    public void deleteGameResult(Long id) {
        gameResultRepository.deleteById(id);
    }
}
