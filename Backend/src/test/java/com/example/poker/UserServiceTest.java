package com.example.poker;
import com.example.poker.PlayerStats.Stats;
import com.example.poker.Users.User;
import com.example.poker.Users.UserRepository;
import com.example.poker.Users.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;



@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testUserEntity() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setChips(10);

        assertEquals("testUser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(10, user.getChips());
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        when(userRepository.existsByUsername("testUser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        userService.createUser(user);

        verify(userRepository, times(1)).save(user);

        // Username already exists
        User existingUser = new User();
        existingUser.setUsername("existingUser");
        existingUser.setEmail("existing@example.com");

        when(userRepository.existsByUsername("existingUser")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(existingUser));
        assertEquals("Username already exists: existingUser", exception.getMessage());
    }



    @Test
    public void testLoginUserNotFound() {
        String username = "nonExistentUser";
        String password = "password";

        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        String result = userService.login(username, password);

        assertEquals("false", result);
    }


    @Test
    public void testUpdateUserNotFound() throws Exception {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setUserId(userId);

        User updatedUser = new User();
        updatedUser.setUsername("updatedUser");
        updatedUser.setEmail("updated@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.getUsername(), result.getUsername());
        assertEquals(updatedUser.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testUpdateUserException() {
        Long userId = 1L;
        User existingUser = createUser("existingUser", "existing@example.com");
        existingUser.setUserId(userId);

        User updatedUser = createUser("updatedUser", "updated@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenThrow(new RuntimeException("Failed to save"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.updateUser(userId, updatedUser));

        assertEquals("Failed to save", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testCreateUserException() {
        User user = createUser("testUser", "test@example.com");

        when(userRepository.existsByUsername("testUser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.save(user)).thenThrow(new RuntimeException("Failed to save"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.createUser(user));

        assertEquals("Failed to save", exception.getMessage());

        verify(userRepository, times(1)).save(user);
    }


    @Test
    public void testLoginFailureWithMultipleUsers() {
        String username = "testUser";
        String password = "password";
        User user1 = createUser(username + "1", "test1@example.com");
        User user2 = createUser(username + "2", "test2@example.com");
        user1.setPassword("wrongPassword");
        user2.setPassword("wrongPassword");
        List<User> userList = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);
        String result = userService.login(username, password);
        assertEquals("false", result);
    }


    @Test
    public void testGetAllUsers() {
        List<User> userList = Arrays.asList(
                createUser("user1", "user1@example.com"),
                createUser("user2", "user2@example.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();

        assertEquals(userList.size(), result.size());
        assertEquals(userList, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = createUser("testUser", "test@example.com");
        user.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testAddChipsSuccess() {
        String username = "testUser";
        int chipsToAdd = 100;

        User user = createUser(username, "test@example.com");
        user.setChips(500);

        when(userRepository.findUserByUsername(username)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        String result = userService.addChips(username, chipsToAdd);

        assertEquals("Chips added successfully", result);
        assertEquals(600, user.getChips());
    }

    @Test
    public void testAddChipsUserNotFound() {
        String username = "nonExistentUser";
        int chipsToAdd = 100;

        when(userRepository.findUserByUsername(username)).thenReturn(null);

        String result = userService.addChips(username, chipsToAdd);

        assertEquals("User not found", result);
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    private User createUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }
    @Test
    public void testGetStatsByUserId() {
        Long userId = 1L;
        Stats userStats = new Stats();  // Assuming you have a Stats class
        User user = createUser("testUser", "test@example.com");
        user.setUserId(userId);
        user.setStats(userStats);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Stats result = userService.getStatsByUserId(userId);

        assertEquals(userStats, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetChipsByUsername() {
        String username = "testUser";
        User user = createUser(username, "test@example.com");
        user.setChips(500);

        when(userRepository.findUserByUsername(username)).thenReturn(user);

        int result = userService.getChipsByUsername(username);

        assertEquals(500, result);
        verify(userRepository, times(1)).findUserByUsername(username);
    }

    @Test
    public void testUpdatePassword() {
        String username = "testUser";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        User user = createUser(username, "test@example.com");
        user.setPassword(oldPassword);

        when(userRepository.findUserByUsername(username)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        String result = userService.updatePassword(username, oldPassword, newPassword);

        assertEquals("Password updated successfully", result);
        assertEquals(newPassword, user.getPassword());
        verify(userRepository, times(1)).findUserByUsername(username);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdatePasswordInvalidCredentials() {
        String username = "testUser";
        String oldPassword = "wrongPassword";
        String newPassword = "newPassword";

        User user = createUser(username, "test@example.com");
        user.setPassword("correctPassword");

        when(userRepository.findUserByUsername(username)).thenReturn(user);

        String result = userService.updatePassword(username, oldPassword, newPassword);

        assertEquals("Invalid credentials", result);
        assertEquals("correctPassword", user.getPassword());
        verify(userRepository, times(1)).findUserByUsername(username);
        verify(userRepository, never()).save(user);  // Password should not be updated
    }

    @Test
    public void testUpdatePasswordUserNotFound() {
        String username = "nonExistentUser";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        when(userRepository.findUserByUsername(username)).thenReturn(null);

        String result = userService.updatePassword(username, oldPassword, newPassword);

        assertEquals("User not found", result);
        verify(userRepository, times(1)).findUserByUsername(username);
        verify(userRepository, never()).save(any(User.class));  // No save should be called
    }

    @Test
    public void testGetUserByUsername() {
        String username = "testUser";
        User user = createUser(username, "test@example.com");

        when(userRepository.findByUsername(username)).thenReturn(user);

        User result = userService.getUserByUsername(username);

        assertEquals(user, result);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testGetUserByEmailFound() {
        String email = "test@example.com";
        User user = createUser("testUser", email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail(email);

        assertEquals(user, result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testGetUserByEmailNotFound() {
        String email = "nonExistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        User result = userService.getUserByEmail(email);

        assertNull(result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testGetUserByIdFound() {
        Long userId = 1L;
        User user = createUser("testUser", "test@example.com");
        user.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserByIdNotFound() {
        Long userId = 999L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(userId);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testUpdateUserWithExistingId() throws Exception {
        Long userId = 1L;
        User existingUser = createUser("existingUser", "existing@example.com");
        existingUser.setUserId(userId);

        User updatedUser = createUser("updatedUser", "updated@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.getUsername(), result.getUsername());
        assertEquals(updatedUser.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testUpdateUserWithNonExistentId() {
        Long userId = 999L;
        User updatedUser = createUser("updatedUser", "updated@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> userService.updateUser(userId, updatedUser));

        assertEquals("User not found with ID: 999", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class)); // No save should be called
    }

    @Test
    public void testCreateUserUsernameExists() {
        User user = createUser("existingUser", "existing@example.com");
        when(userRepository.existsByUsername("existingUser")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        assertEquals("Username already exists: existingUser", exception.getMessage());

        verify(userRepository, times(0)).existsById(anyLong());
        verify(userRepository, times(0)).existsByEmail(anyString());
        verify(userRepository, times(0)).save(user);
    }

    @Test
    public void testCreateUserUserIdExists() {
        User user = createUser("newUser", "new@example.com");
        user.setUserId(1L);
        when(userRepository.existsById(1L)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        assertEquals("User with the same ID already exists: 1", exception.getMessage());

        verify(userRepository, times(1)).existsByUsername("newUser");
        verify(userRepository, times(0)).existsByEmail("new@example.com");
        verify(userRepository, times(0)).save(user);
    }


    @Test
    public void testCreateUserEmailExists() {
        User user = createUser("newUser", "existing@example.com");
        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        assertEquals("Email address is already associated with another user: existing@example.com", exception.getMessage());

        verify(userRepository, times(1)).existsByUsername("newUser");
        verify(userRepository, times(0)).existsById(null);
        verify(userRepository, times(0)).save(user);
    }



}
