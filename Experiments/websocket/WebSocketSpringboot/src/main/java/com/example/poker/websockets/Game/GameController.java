package com.example.poker.websockets.Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.poker.Users.User;
import com.example.poker.Users.User;
import java.util.List;
import com.example.poker.Users.UserRepository;

public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/{gameId}/raise")
    public String raise(@PathVariable Long gameId, @RequestParam String username, @RequestParam int chips) {
        // Use game service to perform raise
        gameService.performRaise(gameId, username, chips);
        return username + " raised by " + chips + " chips.";
    }

    @PostMapping("/{gameId}/check")
    public String check(@PathVariable Long gameId, @RequestParam String username) {
        // Use game service to perform check
        gameService.performCheck(gameId, username);
        return username + " checked.";
    }

    @PostMapping("/{gameId}/fold")
    public String fold(@PathVariable Long gameId, @RequestParam String username) {
        // Use game service to perform fold
        gameService.performFold(gameId, username);
        return username + " folded.";
    }

    @PostMapping("/join-game")
    public String joinLobby(@RequestParam List<String> username) {
        return "Welcome to the game";
    }
}
