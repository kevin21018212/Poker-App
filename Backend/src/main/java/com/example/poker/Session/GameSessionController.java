package com.example.poker.Session;

import com.example.poker.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/game-sessions")
public class GameSessionController {

    //Contains logic for HTTP Requests
    @Autowired
    private GameSessionService gameSessionService;

   //Gets a Game Session given a ID
    @GetMapping("/{id}")
    public Optional<GameSession> getGameSessionById(@PathVariable Long id) {
        return gameSessionService.getGameSessionById(id);
    }

    //Gets Players in a Game Session
    @GetMapping("/{id}/players")
    public List<User> getPlayersByGameSessionId(@PathVariable Long id) {
        return gameSessionService.getPlayersByGameSessionId(id);
    }

    //Creates a Game Session
    @PostMapping("/")
    public GameSession createGameSession(@RequestBody GameSession gameSession) {
        return gameSessionService.createGameSession(gameSession);
    }

    //Adds players to game based on user ID
    @PostMapping("/{id}/add-players")
    public GameSession addPlayersToGameSession(@PathVariable Long id, @RequestBody List<User> players) {
        return gameSessionService.addPlayersToGameSession(id, players);
    }

    //Updates a Game Session based on Game ID
    @PutMapping("/{id}")
    public GameSession updateGameSession(@PathVariable Long id, @RequestBody GameSession request) throws Exception {
        return gameSessionService.updateGameSession(id, request);
    }

    //Deletes a player from the Game Session
    @DeleteMapping("/{sessionId}/remove-player/{playerId}")
    public GameSession removePlayerFromGameSession(
            @PathVariable Long sessionId,
            @PathVariable Long playerId
    ) {
        return gameSessionService.removePlayerFromGameSession(sessionId, playerId);
    }

    //Deletes all players fom a Game Session
    @DeleteMapping("/{id}/clear-players")
    public GameSession clearPlayersInGameSession(@PathVariable Long id) {
        return gameSessionService.clearPlayersInGameSession(id);
    }

   //Deletes a Game Session
    @DeleteMapping("/{id}")
    public String deleteGameSession(@PathVariable Long id) {
        gameSessionService.deleteGameSession(id);
        return "Game session deleted successfully";
    }
}
