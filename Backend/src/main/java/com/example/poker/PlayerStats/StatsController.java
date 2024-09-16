package com.example.poker.PlayerStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatsController {

    //Stats Service Contains Function Logic
    @Autowired
    private StatsService statsService;

    //Http Response Strings
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";


    //Update Stats
    @PutMapping("/{id}")
    public Stats updateStats(@PathVariable Long id, @RequestBody Stats request) {
        return statsService.updateStats(id, request);
    }

    //Delete Stats
    @DeleteMapping("/{id}")
    public String deleteStats(@PathVariable Long id) {
        statsService.deleteStats(id);
        return success;
    }

    //Get Stats
    @GetMapping("/{id}")
    public Stats getStatsById(@PathVariable Long id) {
        return statsService.getStatsById(id);
    }

    //Create a Stats Entry
    @PostMapping("/")
    public String createStats(@RequestBody Stats stats) {
        if (stats == null) {
            return failure;
        }
        statsService.createStats(stats);
        return success;
    }
}
