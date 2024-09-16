package com.example.poker.websockets.Game;


import com.example.poker.Session.GameSession;
import com.example.poker.Session.GameSessionService;
import com.example.poker.Users.User;
import com.example.poker.Users.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final List<User> gamePlayer = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameSessionService gameSessionService;

    public void performPlayerAction( String username, Long gameId, PlayerAction action, int chips) {
        Optional<GameSession> gameSession = gameSessionService.getGameSessionById(gameId);
        if (gameSession.isPresent()) {
            switch (action) {
                case RAISE:
                    performRaise(gameSession.get().getSessionID(), username, chips);
                    break;
                case CHECK:
                    performCheck(gameSession.get().getSessionID(), username);
                    break;
                case FOLD:
                    performFold(gameSession.get().getSessionID(), username);
                    break;
                case CALL:
                    performCall(gameSession.get().getSessionID(), username, chips);

                // Handle other actions
            }
        }
    }

    public Optional<GameSession> getGameById(Long gameId) {
        return gameSessionService.getGameSessionById(gameId);
    }

    public void performFold(Long gameId, String username) {
        Optional<GameSession> gameSesh = gameSessionService.getGameSessionById(gameId);
        User player = null;
        if (gameSesh.isPresent()) {
            player = userRepository.findByUsername(username);
        }
        if(player != null) {

            player.setCard1(0);
            player.setCard2(0);
            gamePlayer.remove(player);
        }
    }

    public void startGame( GameSession gamesessionid){
        
    }

    public void performCheck(Long gameId, String username) {}

    public void performRaise(Long gameId, String username, int chips) {
        Optional<GameSession> gameSesh = gameSessionService.getGameSessionById(gameId);
        User player = null;
        if (gameSesh.isPresent()) {
            player = userRepository.findUserByUsername(username);
        }
        if (player != null) {
            int newChips = player.getChips() - chips;
            if (newChips > 0) {
                player.setChips(newChips);
            }
        }
    }

    public void performCall(Long gameId, String username, int chips){
        Optional<GameSession> gameSesh = gameSessionService.getGameSessionById(gameId);
        User player = null;
        if(gameSesh.isPresent()) {
            player = userRepository.findByUsername(username);
        }
        if (player != null){
            int newChips = player.getChips() - chips;
            if (newChips > 0) {
                player.setChips(newChips);
            }
        }
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public void joinGame(List<String> Usernames) {
        for(int i = 0; i < Usernames.size(); i++) {
            User player = userRepository.findByUsername(String.valueOf(Usernames.indexOf(i)));
            gamePlayer.add(player);
        }
    }
    public User findUserInGameByUserId(Long userId) {
        for (User player : gamePlayer) {
            if (player.getUserId().equals(userId)) {
                return player;
            }
        }
        return null;
    }
}

enum PlayerAction {
    RAISE, CHECK, FOLD, CALL
}
