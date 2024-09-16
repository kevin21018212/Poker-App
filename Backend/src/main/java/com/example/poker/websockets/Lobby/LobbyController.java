package com.example.poker.websockets.Lobby;

import com.example.poker.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/lobby")
public class LobbyController {


    //Contain logic for Requests
    private final LobbyService lobbyService;

    //Constructor
    @Autowired
    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    //Lets a Player Join a lobby as a User
    @PostMapping("/join-lobby")
    public String joinLobby(@RequestParam String username) {
        User user = lobbyService.findUserByUsername(username);
        if (user != null) {
            lobbyService.joinLobby(user);
            return user.getUsername() + " joined the lobby successfully";
        } else {
            return "User not found";
        }
    }

    //Lets a player leave a lobby
    @PostMapping("/leave-lobby")
    public String leaveLobby(@RequestParam String username) {
        User user = lobbyService.findUserByUsername(username);

        if (user != null) {
            lobbyService.leaveLobby(user);
            return user.getUsername() + " left the lobby successfully";
        } else {
            return "User not found";
        }
    }

    //Gets all Players in a lobby
    @GetMapping("/players")
    public List<User> getLobbyPlayers() {
        return lobbyService.getLobbyPlayers();
    }

    //Starts a game if enough players
    @PostMapping("/start-game")
    public String startGame() {
        List<User> lobbyPlayers = lobbyService.getLobbyPlayers();

        if (lobbyPlayers.size() >= 2) {
            lobbyService.notifyStartGame();
            return "Game started!";
        } else {
            return "Not enough players to start the game.";
        }
    }

}

