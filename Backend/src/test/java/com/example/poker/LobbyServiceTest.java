package com.example.poker;

import com.example.poker.Users.User;
import com.example.poker.Users.UserRepository;
import com.example.poker.websockets.Lobby.LobbyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

public class LobbyServiceTest {


    // Mock List of all Users
    @Mock
    private UserRepository userRepository;

    //Used to Check Responses
    @Mock
    private SimpMessagingTemplate messagingTemplate;


    //Lobby Function Logic
    @InjectMocks
    private LobbyService lobbyService;


    //Fakes Connection
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testJoinLobby() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findUserByUsername("testUser")).thenReturn(user);
        lobbyService.joinLobby(user);
        verify(messagingTemplate, times(1)).convertAndSend(eq("/lobby/update"), anyList());
        verify(messagingTemplate, times(1)).convertAndSend(eq("/app/response"), anyMap());
    }

    @Test
    public void testLeaveLobby() {
        User user = new User();
        user.setUsername("testUser");
        List<User> lobbyPlayers = new ArrayList<>();
        lobbyPlayers.add(user);
        when(userRepository.findUserByUsername("testUser")).thenReturn(user);

        lobbyService.leaveLobby(user);
        verify(messagingTemplate, times(1)).convertAndSend(eq("/app/response"), anyMap());
    }

    @Test
    public void testNotifyStartGame() {
        lobbyService.notifyStartGame();
        verify(messagingTemplate, times(1)).convertAndSend(eq("/lobby/start"), eq("Start the game!"));
    }

    @Test
    public void testJoinLobbyUserAlreadyInLobby() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findUserByUsername("testUser")).thenReturn(user);

        lobbyService.joinLobby(user);
        verify(messagingTemplate, times(1)).convertAndSend(eq("/app/response"), anyMap());
    }

    @Test
    public void testLeaveLobbyUserNotInLobby() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findUserByUsername("testUser")).thenReturn(user);

        lobbyService.leaveLobby(user);
        verify(messagingTemplate, times(1)).convertAndSend(eq("/app/response"), anyMap());
    }



    @Test
    public void testJoinLobbyUserNotFound() {
        User user = new User();
        when(userRepository.findUserByUsername("testUser")).thenReturn(user);
        lobbyService.joinLobby(user);
        verify(messagingTemplate, times(1)).convertAndSend(eq("/lobby/update"), anyList());
        verify(messagingTemplate, times(1)).convertAndSend(eq("/app/response"), anyMap());

    }

    @Test
    public void testLeaveLobbyUserNotFound() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findUserByUsername("testUser")).thenReturn(null);

        lobbyService.leaveLobby(user);

        verify(messagingTemplate, times(1)).convertAndSend(eq("/app/response"), anyMap());
    }

    @Test
    public void testGetLobbyPlayers() {
        List<User> players = lobbyService.getLobbyPlayers();
        assertNotSame(lobbyService.getLobbyPlayers(), players);
        assertEquals(lobbyService.getLobbyPlayers(), players);
    }

    @Test
    public void testHandleInvalidUser() {
        lobbyService.joinLobby(null);
        verify(messagingTemplate, times(1)).convertAndSend(eq("/app/response"), anyMap());
    }


    @Test
    public void testLeaveLobbySuccess() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findUserByUsername("testUser")).thenReturn(user);

        lobbyService.joinLobby(user);  // Join lobby first
        lobbyService.leaveLobby(user);

        verify(messagingTemplate, times(2)).convertAndSend(eq("/app/response"), anyMap());
        verify(messagingTemplate, times(2)).convertAndSend(eq("/lobby/update"), anyList());
    }

    @Test
    public void testFindUserByUsername() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findUserByUsername("testUser")).thenReturn(user);

        User result = lobbyService.findUserByUsername("testUser");

        assertEquals(user, result);
    }



    @Test
    public void testHandleUserAlreadyInLobby() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findUserByUsername("testUser")).thenReturn(user);

        lobbyService.joinLobby(user);
        lobbyService.joinLobby(user);

        verify(messagingTemplate, times(2)).convertAndSend(eq("/app/response"), anyMap());
    }

    @Test
    public void testHandleUserNotInLobby() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findUserByUsername("testUser")).thenReturn(user);

        lobbyService.leaveLobby(user);

        verify(messagingTemplate, times(1)).convertAndSend(eq("/app/response"), anyMap());
    }





}
