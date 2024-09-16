package onetoone.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @PostMapping("/")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User request) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Update the fields with the values from the request
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setChips(request.getChips());

            return userRepository.save(user);
        } else {
            throw new Exception("User not found with ID: " + id);
        }
    }
    @GetMapping("/login")
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password) {
        List<User> optionalUser = userRepository.findAll();
        int i = optionalUser.size();

        for(int j = 0; j < i; j++) {
            User user = optionalUser.get(j);
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    return "true";
                }
            }
        }
        return "false";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }
}
