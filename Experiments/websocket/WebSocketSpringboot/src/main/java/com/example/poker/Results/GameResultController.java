package com.example.poker.Results;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/game-results")
public class GameResultController {

    @Autowired
    private GameResultService gameResultService;

    @GetMapping("/")
    public List<GameResult> getAllGameResults() {
        return gameResultService.getAllGameResults();
    }

    @GetMapping("/{id}")
    public Optional<GameResult> getGameResultById(@PathVariable Long id) {
        return gameResultService.getGameResultById(id);
    }

    @PostMapping("/")
    public GameResult createGameResult(@RequestBody GameResult gameResult) {
        return gameResultService.createGameResult(gameResult);
    }

    @PutMapping("/{id}")
    public GameResult updateGameResult(@PathVariable Long id, @RequestBody GameResult request) throws Exception {
        return gameResultService.updateGameResult(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteGameResult(@PathVariable Long id) {
        gameResultService.deleteGameResult(id);
    }
}
