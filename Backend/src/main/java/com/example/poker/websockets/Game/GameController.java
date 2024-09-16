package com.example.poker.websockets.Game;
import com.example.poker.Session.GameSession;
import com.example.poker.websockets.Lobby.LobbyService;

import com.example.poker.Users.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

//import javax.websocket.server.PathParam;
import java.io.IOException;
//import java.net.MalformedURLException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final LobbyService lobbyService;

    @Autowired
    public GameController(GameService gameService, LobbyService lobbyService) {
        this.gameService = gameService;
        this.lobbyService = lobbyService;
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
        gameService.performFold(gameId ,username);
        return username + " folded.";
    }
    @MessageMapping("/start-game")
    @SendTo("/game/start")
    public String startGame() {
        List<User> lobbyPlayers = lobbyService.getLobbyPlayers();

        if (lobbyPlayers.size() >= 2) {
            gameService.startGame(lobbyPlayers);
            lobbyService.getLobbyPlayers().clear();
            return "Game Started successfully";
        } else {
            return "Not enough players to start the game.";
        }
    }

//    @PostMapping("/findWinner")
//    public ArrayList<String> findWinner(@RequestParam String usersAcards, @RequestParam String tableCards){
//        return gameService.findWinner(usersAcards,tableCards);
//    }

    @PostMapping("/testing")
    public int findRanking(@RequestParam String hand){
        return gameService.findRankOfHands(hand);
    }
}

