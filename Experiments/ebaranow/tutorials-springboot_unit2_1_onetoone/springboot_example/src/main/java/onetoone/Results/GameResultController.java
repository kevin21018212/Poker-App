package onetoone.Results;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/game-results")
public class GameResultController {

    @Autowired
    private GameResultRepository gameResultRepository;

    @GetMapping("/")
    public List<GameResult> getAllGameResults() {
        return gameResultRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<GameResult> getGameResultById(@PathVariable Long id) {
        return gameResultRepository.findById(id);
    }

    @PostMapping("/")
    public GameResult createGameResult(@RequestBody GameResult gameResult) {
        return gameResultRepository.save(gameResult);
    }

    @PutMapping("/{id}")
    public GameResult updateGameResult(@PathVariable Long id, @RequestBody GameResult request) throws Exception {
        Optional<GameResult> optionalGameResult = gameResultRepository.findById(id);
        if (optionalGameResult.isPresent()) {
            GameResult gameResult = optionalGameResult.get();
            // Update the fields with the values from the request
            gameResult.setGameSession(request.getGameSession());
            gameResult.setGameType(request.getGameType());
            gameResult.setWinnerUser(request.getWinnerUser());
            gameResult.setLoserUser(request.getLoserUser());

            return gameResultRepository.save(gameResult);
        } else {
            throw new Exception("Game result not found with ID: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public String deleteGameResult(@PathVariable Long id) {
        gameResultRepository.deleteById(id);
        return "Game result deleted successfully";
    }
}
