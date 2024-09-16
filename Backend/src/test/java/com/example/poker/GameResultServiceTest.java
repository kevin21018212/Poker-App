package com.example.poker;

import com.example.poker.PlayerStats.StatsService;
import com.example.poker.Results.GameResult;
import com.example.poker.Results.GameResultRepository;
import com.example.poker.Results.GameResultService;
import com.example.poker.Users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameResultServiceTest {


    //Game Result Function Logic
    @InjectMocks
    private GameResultService gameResultService;

    //Mock Game Results
    @Mock
    private GameResultRepository gameResultRepository;

    //Stats function Logic
    @Mock
    private StatsService statsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateGameResult() {
        // Set up test data
        GameResult gameResultToCreate = new GameResult();
        User winner = new User();
        gameResultToCreate.setWinnerUser(winner);
        List<User> losers = new ArrayList<>();
        losers.add(new User());
        losers.add(new User());
        gameResultToCreate.setLoserUsers(losers);

        // Mock repository save
        when(gameResultRepository.save(gameResultToCreate)).thenReturn(gameResultToCreate);

        // Call the method
        GameResult createdGameResult = gameResultService.createGameResult(gameResultToCreate);

        // Assertions
        assertEquals(gameResultToCreate, createdGameResult);

        // Verify method calls on the statsService
        verify(statsService, times(1)).incrementWins(winner);
        verify(statsService, times(2)).incrementLosses(any());
    }

    @Test
    public void testCreateGameResultWithNoLosers() {
        // Set up test data with no losers
        GameResult gameResultToCreate = new GameResult();
        User winner = new User();
        gameResultToCreate.setWinnerUser(winner);

        // Mock repository save
        when(gameResultRepository.save(gameResultToCreate)).thenReturn(gameResultToCreate);

        // Call the method
        GameResult createdGameResult = gameResultService.createGameResult(gameResultToCreate);

        // Assertions
        assertEquals(gameResultToCreate, createdGameResult);

        // Verify method calls on the statsService
        verify(statsService, times(1)).incrementWins(winner);
        verify(statsService, never()).incrementLosses(any());
    }



    @Test
    public void testCreateGameResultWithNoWinnerAndLosers() {
        // Set up test data with no winner and losers
        GameResult gameResultToCreate = new GameResult();

        // Mock repository save
        when(gameResultRepository.save(gameResultToCreate)).thenReturn(gameResultToCreate);

        // Call the method
        GameResult createdGameResult = gameResultService.createGameResult(gameResultToCreate);

        // Assertions
        assertEquals(gameResultToCreate, createdGameResult);

        // Verify no method calls on the statsService
        verify(statsService, never()).incrementWins(any());
        verify(statsService, never()).incrementLosses(any());
    }
    @Test
    public void testUpdateGameResultWithExistingId() throws Exception {
        Long gameId = 1L;
        GameResult existingGameResult = new GameResult();
        existingGameResult.setGameResultID(gameId);
        when(gameResultRepository.findById(gameId)).thenReturn(Optional.of(existingGameResult));

        GameResult updatedGameResult = new GameResult();
        User updatedWinner = new User();
        updatedGameResult.setWinnerUser(updatedWinner);
        List<User> updatedLosers = new ArrayList<>();
        updatedLosers.add(new User());
        updatedLosers.add(new User());
        updatedGameResult.setLoserUsers(updatedLosers);

        when(gameResultRepository.save(existingGameResult)).thenReturn(existingGameResult);

        // Call the method
        GameResult result = gameResultService.updateGameResult(gameId, updatedGameResult);

        // Assertions
        assertEquals(existingGameResult, result);
        assertEquals(updatedWinner, existingGameResult.getWinnerUser());
        assertEquals(updatedLosers, existingGameResult.getLoserUsers());
        verify(statsService, times(1)).incrementWins(updatedWinner);
        verify(statsService, times(2)).incrementLosses(any());
    }

    @Test
    public void testUpdateGameResultWithNonExistentId() throws Exception {
        Long nonExistentGameId = 2L;
        when(gameResultRepository.findById(nonExistentGameId)).thenReturn(Optional.empty());
        GameResult updatedGameResult = new GameResult();
        updatedGameResult.setGameResultID(nonExistentGameId);
        assertThrows(Exception.class, () -> gameResultService.updateGameResult(nonExistentGameId, updatedGameResult));
        verify(gameResultRepository, never()).save(any());
        verify(statsService, never()).incrementWins(any());
        verify(statsService, never()).incrementLosses(any());
    }

}
