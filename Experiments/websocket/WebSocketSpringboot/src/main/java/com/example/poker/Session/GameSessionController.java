package com.example.poker.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/game-sessions")
public class GameSessionController {

    @Autowired
    private GameSessionService gameSessionService;

    @GetMapping("/")
    public List<GameSession> getAllGameSessions() {
        return gameSessionService.getAllGameSessions();
    }

    @GetMapping("/{id}")
    public Optional<GameSession> getGameSessionById(@PathVariable Long id) {
        return gameSessionService.getGameSessionById(id);
    }

    @PostMapping("/")
    public GameSession createGameSession(@RequestBody GameSession gameSession) {
        return gameSessionService.createGameSession(gameSession);
    }

    @PutMapping("/{id}")
    public GameSession updateGameSession(@PathVariable Long id, @RequestBody GameSession request) throws Exception {
        return gameSessionService.updateGameSession(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteGameSession(@PathVariable Long id) {
        gameSessionService.deleteGameSession(id);
        return "Game session deleted successfully";
    }
}
