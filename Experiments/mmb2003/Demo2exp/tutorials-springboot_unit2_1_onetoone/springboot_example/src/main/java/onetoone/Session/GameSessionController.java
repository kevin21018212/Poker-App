package onetoone.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/game-sessions")
public class GameSessionController {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @GetMapping("/")
    public List<GameSession> getAllGameSessions() {
        return gameSessionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<GameSession> getGameSessionById(@PathVariable Long id) {
        return gameSessionRepository.findById(id);
    }

    @PostMapping("/")
    public GameSession createGameSession(@RequestBody GameSession gameSession) {
        return gameSessionRepository.save(gameSession);
    }

    @PutMapping("/{id}")
    public GameSession updateGameSession(@PathVariable Long id, @RequestBody GameSession request) throws Exception {
        Optional<GameSession> optionalGameSession = gameSessionRepository.findById(id);
        if (optionalGameSession.isPresent()) {
            GameSession gameSession = optionalGameSession.get();
            // Update the fields with the values from the request
            gameSession.setUser(request.getUser());
            gameSession.setStartTime(request.getStartTime());
            gameSession.setEndTime(request.getEndTime());

            return gameSessionRepository.save(gameSession);
        } else {
            throw new Exception("Game session not found with ID: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public String deleteGameSession(@PathVariable Long id) {
        gameSessionRepository.deleteById(id);
        return "Game session deleted successfully";
    }
}
