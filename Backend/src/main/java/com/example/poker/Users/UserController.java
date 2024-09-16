package com.example.poker.Users;

import com.example.poker.PlayerStats.Stats;
import com.example.poker.Session.GameSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }
    //works
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    //works
    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    //works
    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }
    //works
    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
    //works
    @GetMapping("/stats/{userId}")
    public Stats getStatsByUserId(@PathVariable Long userId) {
        return userService.getStatsByUserId(userId);
    }
    //works
    @GetMapping("/chips/{username}")
    public int getChipsByUsername(@PathVariable String username) {
        return userService.getChipsByUsername(username);
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    //works
    @PostMapping("/addChips")
    public String addChips(@RequestParam String username, @RequestParam int chips) {
        return userService.addChips(username, chips);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User request) throws Exception {
        return userService.updateUser(id, request);
    }

    @PostMapping("/new/{username}")
    public User FindIDbyUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return userService.updatePassword(username, oldPassword, newPassword);
    }


}
