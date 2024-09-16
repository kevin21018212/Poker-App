package com.example.poker;

import com.example.poker.Users.User;
import com.example.poker.websockets.Lobby.LobbyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class LobbyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LobbyService lobbyService;

    @Test
    public void testJoinLobby() throws Exception {
        // Mocking the service behavior
        User user = new User();
        user.setUsername("testUser");
        when(lobbyService.findUserByUsername("testUser")).thenReturn(user);

        // Performing the request and verifying the response
        mockMvc.perform(MockMvcRequestBuilders.post("/lobby/join-lobby")
                        .param("username", "testUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("testUser joined the lobby successfully"));
    }

    @Test
    public void testLeaveLobby() throws Exception {
        // Mocking the service behavior
        User user = new User();
        user.setUsername("testUser");
        when(lobbyService.findUserByUsername("testUser")).thenReturn(user);

        // Performing the request and verifying the response
        mockMvc.perform(MockMvcRequestBuilders.post("/lobby/leave-lobby")
                        .param("username", "testUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("testUser left the lobby successfully"));
    }

    @Test
    public void testGetLobbyPlayers() throws Exception {
        // Mocking the service behavior
        List<User> players = Collections.singletonList(createUser("player1"));
        when(lobbyService.getLobbyPlayers()).thenReturn(players);

        // Performing the request and verifying the response
        mockMvc.perform(MockMvcRequestBuilders.get("/lobby/players")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("player1"));
    }

    @Test
    public void testStartGame() throws Exception {
        // Mocking the service behavior
        List<User> lobbyPlayers = createUsers("player1", "player2");
        when(lobbyService.getLobbyPlayers()).thenReturn(lobbyPlayers);

        // Performing the request and verifying the response
        mockMvc.perform(MockMvcRequestBuilders.post("/lobby/start-game")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Game started!"));
    }

    private List<User> createUsers(String... usernames) {
        return Arrays.stream(usernames)
                .map(this::createUser)
                .collect(Collectors.toList());
    }


    private User createUser(String username) {
        User user = new User();
        user.setUsername(username);
        return user;
    }



}
