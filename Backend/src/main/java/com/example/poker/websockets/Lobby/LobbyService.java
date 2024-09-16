package com.example.poker.websockets.Lobby;

import com.example.poker.Users.User;
import com.example.poker.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LobbyService {


    //List of all connected players
    private static final List<User> lobbyPlayers = new ArrayList<>();

    //List of all Users
    @Autowired
    private UserRepository userRepository;

    //Sends messages to all connected Clients
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    //Logic for joining a lobby and updating connected Clients
    public void joinLobby(User user) {
        if (user == null) {
            handleInvalidUser();
            return;
        }

        if (lobbyPlayers.contains(user)) {
            handleUserAlreadyInLobby(user);
        } else {
            lobbyPlayers.add(user);
            notifyLobbyUpdate();
            notifyResponse(user.getUsername() + " joined the lobby successfully");
        }
    }

    //Logic for leaving a lobby and updating connected Clients
    public void leaveLobby(User user) {
        if (lobbyPlayers.contains(user)) {
            lobbyPlayers.remove(user);
            notifyLobbyUpdate();
            notifyResponse(user.getUsername() + " left the lobby successfully");
        } else {
            handleUserNotInLobby();
        }
    }

    //Logic to get all Lobby Players
    public List<User> getLobbyPlayers() {
        return new ArrayList<>(lobbyPlayers);
    }

   //Logic to notify all Clients the game has started
    public void notifyStartGame() {
        if (lobbyPlayers.size() >= 2) {
            messagingTemplate.convertAndSend("/lobby/start", "Start the game!");
        } else {
            notifyResponse("Not enough players to start the game.");
        }
    }


    //Finds a user based on username
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    //Response for null User
    private void handleInvalidUser() {
        notifyResponse("User not found");
    }
    //Response if User is already in Lobby
    private void handleUserAlreadyInLobby(User user) {
        notifyResponse(user.getUsername() + " is already in the lobby");
    }

    //Response if User is not in Lobby
    private void handleUserNotInLobby() {
        notifyResponse("User not found in the lobby");
    }


    //Notify Connected Clients
    private void notifyResponse(String message) {
        messagingTemplate.convertAndSend("/app/response", createResponseMessage(message));
    }

    private void notifyLobbyUpdate() {
        messagingTemplate.convertAndSend("/lobby/update", new ArrayList<>(lobbyPlayers));
    }


    //Response messages sent to clients
    private Map<String, String> createResponseMessage(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }
}
