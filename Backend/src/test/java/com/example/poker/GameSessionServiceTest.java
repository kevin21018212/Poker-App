package com.example.poker;

import com.example.poker.Session.GameSession;
import com.example.poker.Session.GameSessionRepository;
import com.example.poker.Session.GameSessionService;
import com.example.poker.Users.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.dao.EmptyResultDataAccessException;


@SpringBootTest
public class GameSessionServiceTest {

    //Game Session function logic
    @InjectMocks
    private GameSessionService gameSessionService;

    //Mock List of all Game Sessions
    @Mock
    private GameSessionRepository gameSessionRepository;

    @Test
    public void testGetAllGameSessions() {
        List<GameSession> expectedGameSessions = new ArrayList<>();
        when(gameSessionRepository.findAll()).thenReturn(expectedGameSessions);

        List<GameSession> actualGameSessions = gameSessionService.getAllGameSessions();

        assertEquals(expectedGameSessions, actualGameSessions);
    }

    @Test
    public void testGetGameSessionById() {
        Long sessionId = 1L;
        GameSession expectedGameSession = new GameSession();
        expectedGameSession.setSessionID(sessionId);
        when(gameSessionRepository.findById(sessionId)).thenReturn(Optional.of(expectedGameSession));
        Optional<GameSession> actualGameSession = gameSessionService.getGameSessionById(sessionId);
        assertTrue(actualGameSession.isPresent());
        assertEquals(expectedGameSession, actualGameSession.get());
        Long nonExistentSessionId = 2L;
        when(gameSessionRepository.findById(nonExistentSessionId)).thenReturn(Optional.empty());
        Optional<GameSession> nonExistentGameSession = gameSessionService.getGameSessionById(nonExistentSessionId);
        assertFalse(nonExistentGameSession.isPresent());
    }

    @Test
    public void testCreateGameSession() {
        GameSession gameSessionToCreate = new GameSession();
        User user = new User();
        gameSessionToCreate.setUser(user);
        when(gameSessionRepository.save(gameSessionToCreate)).thenReturn(gameSessionToCreate);
        GameSession createdGameSession = gameSessionService.createGameSession(gameSessionToCreate);
        assertEquals(gameSessionToCreate, createdGameSession);
    }

    @Test
    public void testAddPlayersToGameSession() {
        Long sessionId = 1L;
        GameSession gameSession = new GameSession();
        gameSession.setSessionID(sessionId);
        List<User> players = new ArrayList<>();
        players.add(new User());
        players.add(new User());
        when(gameSessionRepository.findById(sessionId)).thenReturn(Optional.of(gameSession));
        when(gameSessionRepository.save(any(GameSession.class))).thenReturn(gameSession);
        GameSession updatedGameSession = gameSessionService.addPlayersToGameSession(sessionId, players);
        assertEquals(gameSession, updatedGameSession);
        assertEquals(players.size(), updatedGameSession.getPlayers().size());
    }

    @Test
    public void testUpdateGameSession() throws Exception {
        Long sessionId = 1L;
        GameSession gameSession = new GameSession();
        gameSession.setSessionID(sessionId);
        gameSession.setUser(new User());
        when(gameSessionRepository.findById(sessionId)).thenReturn(Optional.of(gameSession));
        when(gameSessionRepository.save(any(GameSession.class))).thenReturn(gameSession);

        GameSession updatedGameSession = gameSessionService.updateGameSession(sessionId, gameSession);
        assertEquals(gameSession, updatedGameSession);
    }

    @Test
    public void testDeleteGameSession() {
        Long sessionId = 1L;
        doNothing().when(gameSessionRepository).deleteById(sessionId);
        gameSessionService.deleteGameSession(sessionId);
        verify(gameSessionRepository, times(1)).deleteById(sessionId);
    }
    @Test
    public void testAddPlayerToNonExistentGameSession() {
        Long nonExistentSessionId = 100L;
        List<User> players = new ArrayList<>();
        players.add(new User());
        when(gameSessionRepository.findById(nonExistentSessionId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                gameSessionService.addPlayersToGameSession(nonExistentSessionId, players));
    }

    @Test
    public void testUpdateNonExistentGameSession() {
        Long nonExistentSessionId = 100L;
        GameSession gameSession = new GameSession();
        gameSession.setSessionID(nonExistentSessionId);
        when(gameSessionRepository.findById(nonExistentSessionId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () ->
                gameSessionService.updateGameSession(nonExistentSessionId, gameSession));
    }

    @Test
    public void testUpdateGameSessionWithNullUser() {
        Long sessionId = 1L;
        GameSession gameSession = new GameSession();
        gameSession.setSessionID(sessionId);
        GameSession request = new GameSession();
        request.setUser(null);
        when(gameSessionRepository.findById(sessionId)).thenReturn(Optional.of(gameSession));
        when(gameSessionRepository.save(any(GameSession.class))).thenReturn(gameSession);
        assertDoesNotThrow(() -> gameSessionService.updateGameSession(sessionId, request));

        assertNull(gameSession.getUser());
    }

    @Test
    public void testDeleteNonExistentGameSession() {
        Long nonExistentSessionId = 100L;
        doThrow(EmptyResultDataAccessException.class)
                .when(gameSessionRepository).deleteById(nonExistentSessionId);
        assertThrows(RuntimeException.class, () ->
                gameSessionService.deleteGameSession(nonExistentSessionId));
    }

    @Test
    public void testGetPlayersByGameSessionId() {
        Long sessionId = 1L;
        GameSession gameSession = new GameSession();
        gameSession.setSessionID(sessionId);
        List<User> players = new ArrayList<>();
        players.add(new User());
        players.add(new User());
        gameSession.setPlayers(players);

        when(gameSessionRepository.findById(sessionId)).thenReturn(Optional.of(gameSession));

        List<User> retrievedPlayers = gameSessionService.getPlayersByGameSessionId(sessionId);

        assertEquals(players.size(), retrievedPlayers.size());
        assertEquals(players, retrievedPlayers);
    }

    @Test
    public void testRemovePlayerFromGameSession() {
        Long sessionId = 1L;
        Long playerId = 2L;

        GameSession gameSession = new GameSession();
        gameSession.setSessionID(sessionId);
        List<User> players = new ArrayList<>();
        User playerToRemove = new User();
        playerToRemove.setUserId(playerId);
        players.add(playerToRemove);
        players.add(new User());
        gameSession.setPlayers(players);

        when(gameSessionRepository.findById(sessionId)).thenReturn(Optional.of(gameSession));
        when(gameSessionRepository.save(any(GameSession.class))).thenReturn(gameSession);

        GameSession updatedGameSession = gameSessionService.removePlayerFromGameSession(sessionId, playerId);

        assertEquals(players.size() , updatedGameSession.getPlayers().size());
        assertFalse(updatedGameSession.getPlayers().contains(playerToRemove));
    }

    @Test
    public void testClearPlayersInGameSession() {
        Long sessionId = 1L;

        GameSession gameSession = new GameSession();
        gameSession.setSessionID(sessionId);
        List<User> players = new ArrayList<>();
        players.add(new User());
        players.add(new User());
        gameSession.setPlayers(players);

        when(gameSessionRepository.findById(sessionId)).thenReturn(Optional.of(gameSession));
        when(gameSessionRepository.save(any(GameSession.class))).thenReturn(gameSession);

        GameSession clearedGameSession = gameSessionService.clearPlayersInGameSession(sessionId);

        assertTrue(clearedGameSession.getPlayers().isEmpty());
    }
}

