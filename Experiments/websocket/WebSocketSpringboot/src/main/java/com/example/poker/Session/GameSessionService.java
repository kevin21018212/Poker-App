package com.example.poker.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameSessionService {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    public List<GameSession> getAllGameSessions() {
        return gameSessionRepository.findAll();
    }

    public Optional<GameSession> getGameSessionById(Long id) {
        return gameSessionRepository.findById(id);
    }

    public GameSession createGameSession(GameSession gameSession) {

        // gameSession.setPlayers(/* set list of players */);
        // gameSession.setGameResult(/* set game result */);
        return gameSessionRepository.save(gameSession);
    }

    public GameSession updateGameSession(Long id, GameSession request) throws Exception {
        Optional<GameSession> optionalGameSession = gameSessionRepository.findById(id);
        if (optionalGameSession.isPresent()) {
            GameSession gameSession = optionalGameSession.get();
            // Update the fields with the values from the request
            gameSession.setUser(request.getUser());



            return gameSessionRepository.save(gameSession);
        } else {
            throw new Exception("Game session not found with ID: " + id);
        }
    }

    public void deleteGameSession(Long id) {
        gameSessionRepository.deleteById(id);
    }
}
