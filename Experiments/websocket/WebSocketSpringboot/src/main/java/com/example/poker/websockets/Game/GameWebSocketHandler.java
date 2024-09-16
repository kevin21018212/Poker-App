package com.example.poker.websockets.Game;

import com.example.poker.Session.GameSession;
import com.example.poker.Users.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GameWebSocketHandler {

    private final GameService gameService;


    @Autowired
    public GameWebSocketHandler(GameService gameService) {
        this.gameService = gameService;

    }

//    public void handlePlayerAction(User player, Long gameId, PlayerAction action, int chips) {
//        // handle player actions: raise, check, and fold
//        gameService.performPlayerAction(player, gameId, action, chips);
//
//        // send updates to all players in the game
//        broadcastGameUpdates(gameId);
//    }

    public void handlePlayerDisconnect(User player, Long gameId) {


        // logic to remove the player from the active player list and manage player state

        // send updates to all players in the game
        broadcastGameUpdates(gameId);
    }

    public void broadcastGameUpdates(Long gameId) {
        //  logic to send updates to all players in the game


        //  active players, cards on the board, game winner, game loser, and player turn

        Optional<GameSession> game = gameService.getGameById(gameId);
        if (game != null) {
            // You need to define how to send updates to WebSocket clients
            // Use messagingTemplate.convertAndSendToUser(...) to send updates to specific topics
            //THIS IS BALLS
        }
    }
}
