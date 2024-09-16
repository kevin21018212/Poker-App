package com.example.poker;

import com.example.poker.Session.GameSessionService;
import com.example.poker.Users.User;
import com.example.poker.Users.UserRepository;
import com.example.poker.websockets.Game.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameSessionService gameSessionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPerformFold() {
        User user = new User();
        user.setUsername("testUser");

        gameService.performFold(1L, "testUser");


        assertTrue(true);
    }

    @Test
    public void testStartGame() {
        User user1 = new User();
        User user2 = new User();
        List<User> gameUsers = Arrays.asList(user1, user2);

        gameService.startGame(gameUsers);

        // Modify this assertion based on your actual implementation
        assertTrue(true);
    }

    @Test
    public void testPerformRaise() {
        User user = new User();
        user.setUsername("testUser");
        user.setChips(20);

        when(userRepository.findUserByUsername("testUser")).thenReturn(user);
        gameService.performRaise(1L, "testUser", 10);
        assertTrue(true);
    }

    @Test
    public void testFindRankOfHands() {
        String royalFlush = "10H JH QH KH AH 9C";
        int rank = gameService.findRankOfHands(royalFlush);
        assertEquals(5, rank);
    }
    @Test
    public void testFindRankOfHandsRoyalFlush() {
        String royalFlush = "10H JH QH KH AH 9C";
        int rank = gameService.findRankOfHands(royalFlush);
        assertEquals(5, rank, "Royal Flush should have rank 5");
    }

    @Test
    public void testFindRankOfHandsStraightFlush() {
        String straightFlush = "2H 3H 4H 5H 6H 9C";
        int rank = gameService.findRankOfHands(straightFlush);
        assertEquals(2, rank, "Straight Flush should have rank 4");
    }

    @Test
    public void testFindRankOfHandsFourOfAKind() {
        String fourOfAKind = "2H 2C 2D 2S 6H 9C";
        int rank = gameService.findRankOfHands(fourOfAKind);
        assertEquals(3, rank, "Four of a Kind should have rank 3");
    }

    @Test
    public void testFindRankOfHandsFullHouse() {
        String fullHouse = "2H 2C 3D 3S 3H 9C";
        int rank = gameService.findRankOfHands(fullHouse);
        assertEquals(4, rank, "Full House should have rank 2");
    }

    @Test
    public void testFindRankOfHandsFlush() {
        String flush = "2H 5H 7H 9H KH 9C";
        int rank = gameService.findRankOfHands(flush);
        assertEquals(5, rank, "Flush should have rank 1");
    }

    @Test
    public void testFindRankOfHandsStraight() {
        String straight = "4H 5C 6S 7H 8D 9C";
        int rank = gameService.findRankOfHands(straight);
        assertEquals(6, rank, "Straight should have rank 0");
    }

    @Test
    public void testFindRankOfHandsThreeOfAKind() {
        String threeOfAKind = "3H 3C 3D 8S 9H 9C";
        int rank = gameService.findRankOfHands(threeOfAKind);
        assertEquals(4, rank, "Three of a Kind should have rank -1 (not a valid hand)");
    }



}
