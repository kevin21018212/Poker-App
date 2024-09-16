package com.example.poker;

import com.example.poker.PlayerStats.Stats;
import com.example.poker.PlayerStats.StatsRepository;
import com.example.poker.PlayerStats.StatsService;
import com.example.poker.Users.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StatsServiceTest {

    //Stats function Logic
    @Autowired
    private StatsService statsService;

    //Stats Tables
    @MockBean
    private StatsRepository statsRepository;


    @Test
    public void testCreateStats() {
        Stats statsToCreate = new Stats();
        statsToCreate.setGamesPlayed(10);
        statsToCreate.setWins(5);
        statsToCreate.setLosses(5);
        // Mock repository save
        when(statsRepository.save(statsToCreate)).thenReturn(statsToCreate);
        statsService.createStats(statsToCreate);
        verify(statsRepository, times(1)).save(statsToCreate);
    }

    @Test
    public void testUpdateStats() {
        Long statsId = 1L;
        Stats existingStats = new Stats();
        existingStats.setStatsId(statsId);
        existingStats.setGamesPlayed(10);
        existingStats.setWins(5);
        existingStats.setLosses(5);
        Stats updatedStats = new Stats();
        updatedStats.setGamesPlayed(15);
        updatedStats.setWins(8);
        updatedStats.setLosses(7);
        when(statsRepository.findById(statsId)).thenReturn(java.util.Optional.of(existingStats));
        when(statsRepository.save(existingStats)).thenReturn(updatedStats);
        Stats result = statsService.updateStats(statsId, updatedStats);
        assertNotNull(result);
        assertEquals(updatedStats, result);
        verify(statsRepository, times(1)).findById(statsId);
        verify(statsRepository, times(1)).save(existingStats);
        when(statsRepository.findById(2L)).thenReturn(java.util.Optional.empty());
        result = statsService.updateStats(2L, updatedStats);
        assertNull(result);
    }

    @Test
    public void testDeleteStats() {
        Long statsId = 1L;
        statsService.deleteStats(statsId);
        verify(statsRepository, times(1)).deleteById(statsId);
    }

    @Test
    public void testGetStatsById() {
        Long statsId = 1L;
        Stats expectedStats = new Stats();
        expectedStats.setStatsId(statsId);
        expectedStats.setGamesPlayed(10);
        expectedStats.setWins(5);
        expectedStats.setLosses(5);

        when(statsRepository.findById(statsId)).thenReturn(Optional.of(expectedStats));

        Stats result = statsService.getStatsById(statsId);

        assertNotNull(result);
        assertEquals(expectedStats, result);
        verify(statsRepository, times(1)).findById(statsId);
    }

    @Test
    public void testGetStatsByIdNotFound() {
        Long statsId = 2L;

        when(statsRepository.findById(statsId)).thenReturn(Optional.empty());

        Stats result = statsService.getStatsById(statsId);

        assertNull(result);
        verify(statsRepository, times(1)).findById(statsId);
    }

    @Test
    public void testIncrementWins() {
        User user = new User();
        Stats stats = new Stats();
        stats.setWins(5);
        user.setStats(stats);

        when(statsRepository.save(stats)).thenReturn(stats);

        statsService.incrementWins(user);

        assertEquals(6, user.getStats().getWins());
        verify(statsRepository, times(1)).save(stats);
    }

    @Test
    public void testIncrementLosses() {
        User user = new User();
        Stats stats = new Stats();
        stats.setLosses(3);
        user.setStats(stats);

        when(statsRepository.save(stats)).thenReturn(stats);

        statsService.incrementLosses(user);

        assertEquals(4, user.getStats().getLosses());
        verify(statsRepository, times(1)).save(stats);
    }

    @Test
    public void testIncrementWinsWithNullStats() {
        User user = new User();

        assertThrows(RuntimeException.class, () -> statsService.incrementWins(user));
        verify(statsRepository, times(0)).save(any());
    }

    @Test
    public void testIncrementLossesWithNullStats() {
        User user = new User();

        assertThrows(RuntimeException.class, () -> statsService.incrementLosses(user));
        verify(statsRepository, times(0)).save(any());
    }




}
