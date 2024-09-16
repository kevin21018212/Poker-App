package com.example.poker.websockets.Lobby;

import com.example.poker.Session.GameSession;
import com.example.poker.Users.User;
import com.example.poker.Users.UserRepository;
import com.example.poker.Session.GameSessionRepository;
import com.example.poker.websockets.Game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LobbyService {
    private final List<User> lobbyPlayers = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameSessionRepository gameSessionRepository;



    @Autowired
    private GameService gameService;

    public void joinLobby(Long userId) {
        User player = userRepository.findById(userId).orElse(null);

        if (player != null) {
            if (!lobbyPlayers.contains(player)) {
                lobbyPlayers.add(player);
            }
        }
    }

    public void leaveLobby(Long userId) {
        User playerToRemove = null;
        for (User player : lobbyPlayers) {
            if (player.getUserId().equals(userId)) {
                playerToRemove = player;
                break;
            }
        }

        if (playerToRemove != null) {
            lobbyPlayers.remove(playerToRemove);
         ;
        }
    }



//    public GameSession startGame() {
//        if (lobbyPlayers.size() >= 2) {
//            GameSession gameSession = new GameSession();
//            gameSessionRepository.save(gameSession);
//            for (User player : lobbyPlayers) {
//                gameSession.addPlayer(player);
//            }
//            lobbyPlayers.clear();
//
//            return gameSession;
//        }
//        return null;
//    }





    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }



    public List<User> getLobbyPlayers() {
        return lobbyPlayers;
    }

    public User findUserInLobbyByUserId(Long userId) {
        for (User player : lobbyPlayers) {
            if (player.getUserId().equals(userId)) {
                return player;
            }
        }
        return null;
    }


}
