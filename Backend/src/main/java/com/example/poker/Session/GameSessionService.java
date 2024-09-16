package com.example.poker.Session;

import com.example.poker.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GameSessionService {

    //List of all Game Sessions
    @Autowired
    private GameSessionRepository gameSessionRepository;

    //Gets all Game Sessions
    public List<GameSession> getAllGameSessions() {
        return gameSessionRepository.findAll();
    }

    //Gets Game Session based on ID
    public Optional<GameSession> getGameSessionById(Long id) {
        return gameSessionRepository.findById(id);
    }

    //Gets Players in a Game Session
    public List<User> getPlayersByGameSessionId(Long id) {
        Optional<GameSession> optionalGameSession = gameSessionRepository.findById(id);
        return optionalGameSession.map(GameSession::getPlayers).orElse(Collections.emptyList());
    }

    //Creates a Game Session
    public GameSession createGameSession(GameSession gameSession) { return gameSessionRepository.save(gameSession);
    }

    //Adds players to a Game Session
    public GameSession addPlayersToGameSession(Long id, List<User> players) {
        Optional<GameSession> optionalGameSession = gameSessionRepository.findById(id);
        if (optionalGameSession.isPresent()) {
            GameSession gameSession = optionalGameSession.get();
            gameSession.getPlayers().addAll(players);
            return gameSessionRepository.save(gameSession);
        } else {
            throw new RuntimeException("Game session not found with ID: " + id);
        }
    }

    //Updates a Existing Game Session
    public GameSession updateGameSession(Long id, GameSession request) throws Exception {
        Optional<GameSession> optionalGameSession = gameSessionRepository.findById(id);
        if (optionalGameSession.isPresent()) {
            GameSession gameSession = optionalGameSession.get();
            User newUser = request.getUser();
            if (newUser != null) {
                gameSession.setUser(newUser);
            }
            return gameSessionRepository.save(gameSession);
        } else {
            throw new Exception("Game session not found with ID: " + id);
        }
    }


    //Removes a single player from a gameSession
    public GameSession removePlayerFromGameSession(Long sessionId, Long playerId) {
        Optional<GameSession> optionalGameSession = gameSessionRepository.findById(sessionId);
        if (optionalGameSession.isPresent()) {
            GameSession gameSession = optionalGameSession.get();
            List<User> players = gameSession.getPlayers();
            players.removeIf(user -> user.getUserId() != null && user.getUserId().equals(playerId));

            return gameSessionRepository.save(gameSession);
        } else {
            throw new RuntimeException("Game session not found with ID: " + sessionId);
        }
    }

    //Removes all players from a GameSession
    public GameSession clearPlayersInGameSession(Long id) {
        Optional<GameSession> optionalGameSession = gameSessionRepository.findById(id);
        if (optionalGameSession.isPresent()) {
            GameSession gameSession = optionalGameSession.get();
            gameSession.clearPlayers();
            return gameSessionRepository.save(gameSession);
        } else {
            throw new RuntimeException("Game session not found with ID: " + id);
        }
    }

   //Deletes a Game Session
    public void deleteGameSession(Long id) {
        gameSessionRepository.deleteById(id);
    }
}
