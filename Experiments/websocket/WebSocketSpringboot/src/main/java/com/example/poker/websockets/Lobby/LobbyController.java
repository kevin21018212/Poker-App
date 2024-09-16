package com.example.poker.websockets.Lobby;

import com.example.poker.Session.GameSession;
import com.example.poker.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lobby")
public class
LobbyController {

    private final LobbyService lobbyService;

    @Autowired
    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @PostMapping("/join-lobby")
    public String joinLobby(@RequestParam String username) {
        User user = lobbyService.findUserByUsername(username);

        if (user != null) {
            User existingUserInLobby = lobbyService.findUserInLobbyByUserId(user.getUserId());

            if (existingUserInLobby != null) {
                return user.getUsername() + " is already in the lobby";
            } else {
                lobbyService.joinLobby(user.getUserId());
                return user.getUsername() + " joined the lobby successfully";
            }
        } else {
            return "User not found";
        }
    }


    @GetMapping("/players")
    public List<User> getLobbyPlayers() {
        return lobbyService.getLobbyPlayers();
    }

    @PostMapping("/leave-lobby")
    public String leaveLobby(@RequestParam String username) {
        User user = lobbyService.findUserByUsername(username);

        if (user != null) {
            lobbyService.leaveLobby(user.getUserId());
            return user.getUsername() + " left the lobby successfully";
        } else {
            return "User not found";
        }
    }


    @PostMapping("/start-game")
    public String startGame() {
        List<User> lobbyPlayers = lobbyService.getLobbyPlayers();

        if (lobbyPlayers.size() >= 2) {
//            GameSession gameSession = lobbyService.startGame();
            return "Game started!";
        } else {
            return "Not enough players to start the game.";
        }
    }
}

