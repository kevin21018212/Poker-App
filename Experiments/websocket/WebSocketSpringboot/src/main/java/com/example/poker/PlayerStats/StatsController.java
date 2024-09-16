package com.example.poker.PlayerStats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";



    @GetMapping("/{id}")
    public Stats getStatsById(@PathVariable Long id) {
        return statsService.getStatsById(id);
    }

    @PostMapping("/")
    public String createStats(@RequestBody Stats stats) {
        if (stats == null) {
            return failure;
        }
        statsService.createStats(stats);
        return success;
    }

    @PutMapping("/{id}")
    public Stats updateStats(@PathVariable Long id, @RequestBody Stats request) {
        Stats updatedStats = statsService.updateStats(id, request);
        if (updatedStats == null) {
            // Stats not found
            return null;
        }
        return updatedStats;
    }

    @DeleteMapping("/{id}")
    public String deleteStats(@PathVariable Long id) {
        statsService.deleteStats(id);
        return success;
    }
}
