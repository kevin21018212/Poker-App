package com.example.poker.Results;

import com.example.poker.Users.User;
import com.example.poker.PlayerStats.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GameResultService {

    //List of all Game Results
    @Autowired
    private GameResultRepository gameResultRepository;

    @Autowired
    private StatsService statsService;

    //Gets a List of all GameResults in table
    public List<GameResult> getAllGameResults() {
        return gameResultRepository.findAll();
    }

    //Gets a GameResult based on ID
    public Optional<GameResult> getGameResultById(Long id) {
        return gameResultRepository.findById(id);
    }

    //Creates a new Game Result
    public GameResult createGameResult(GameResult gameResult) {
        User winner = gameResult.getWinnerUser();
        List<User> losers = gameResult.getLoserUsers();
        if (winner != null) {
            statsService.incrementWins(winner);
        }
        if (losers != null) {
            for (User loser : losers) {
                if (loser != null) {
                    statsService.incrementLosses(loser);
                }
            }
        }
        return gameResultRepository.save(gameResult);
    }


    //Updates a Game Result given a ID
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

    //Deletes a GameResult Given a ID
    public void deleteGameResult(Long id) {
        gameResultRepository.deleteById(id);
    }
}
