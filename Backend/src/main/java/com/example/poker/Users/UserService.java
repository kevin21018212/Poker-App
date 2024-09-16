package com.example.poker.Users;

import com.example.poker.PlayerStats.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.poker.Users.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User updateUser(Long id, User request) throws Exception {
        return userRepository.findById(id).map(user -> {
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setChips(request.getChips());
            return userRepository.save(user);
        }).orElseThrow(() -> new Exception("User not found with ID: " + id));
    }

    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }

        if (user.getUserId() != null && userRepository.existsById(user.getUserId())) {
            throw new IllegalArgumentException("User with the same ID already exists: " + user.getUserId());
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email address is already associated with another user: " + user.getEmail());
        }

        return userRepository.save(user);
    }

    public String login(String username, String password) {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return "true";
            }
        }
        return "false";
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public String addChips(String username, int chips) {
      User optionalUser = userRepository.findUserByUsername(username);

        if (optionalUser!=null) {
            int currentChips = optionalUser.getChips();
            int newChips = currentChips + chips;
            optionalUser.setChips(newChips);
            userRepository.save(optionalUser);
            return "Chips added successfully";
        } else {
            return "User not found";
        }
    }


    public Stats getStatsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? user.getStats() : null;
    }


    public int getChipsByUsername(String username) {
       User optionalUser = userRepository.findUserByUsername(username);

        return optionalUser.getChips();
    }


    public String updatePassword(String username, String oldPassword, String newPassword) {
        User optionalUser = userRepository.findUserByUsername(username);

        if (optionalUser!=null) {
            if (optionalUser.getPassword().equals(oldPassword)) {
                optionalUser.setPassword(newPassword);
                userRepository.save(optionalUser);
                return "Password updated successfully";
            } else {
                return "Invalid credentials";
            }
        } else {
            return "User not found";
        }
    }



}
