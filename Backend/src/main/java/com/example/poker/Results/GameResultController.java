package com.example.poker.Results;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/game-results")
public class GameResultController {

    //Contains logic for Requests
    @Autowired
    private GameResultService gameResultService;

    //Gets all Game Results
    @GetMapping("/")
    public List<GameResult> getAllGameResults() {
        return gameResultService.getAllGameResults();
    }

    //Gets game Results based on ID
    @GetMapping("/{id}")
    public Optional<GameResult> getGameResultById(@PathVariable Long id) {
        return gameResultService.getGameResultById(id);
    }

    //Makes a game Result
    @PostMapping("/")
    public GameResult createGameResult(@RequestBody GameResult gameResult) {
        return gameResultService.createGameResult(gameResult);
    }

    //Updates a Game Result
    @PutMapping("/{id}")
    public GameResult updateGameResult(@PathVariable Long id, @RequestBody GameResult request) throws Exception {
        return gameResultService.updateGameResult(id, request);
    }

    //Deletes a Game Result
    @DeleteMapping("/{id}")
    public void deleteGameResult(@PathVariable Long id) {
        gameResultService.deleteGameResult(id);
    }
}
