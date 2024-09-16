package onetoone.PlayerStats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsRepository statsRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping("/")
    public List<Stats> getAllStats() {
        return statsRepository.findAll();
    }

    @GetMapping("/{id}")
    public Stats getStatsById(@PathVariable Long id) {
        return statsRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public String createStats(@RequestBody Stats stats) {
        if (stats == null) {
            return failure;
        }
        statsRepository.save(stats);
        return success;
    }

    @PutMapping("/{id}")
    public Stats updateStats(@PathVariable Long id, @RequestBody Stats request) {
        Stats stats = statsRepository.findById(id).orElse(null);
        if (stats == null) {
            return null;
        }

        // Update the fields with the values from the request
        stats.setGamesPlayed(request.getGamesPlayed());
        stats.setWins(request.getWins());
        stats.setLosses(request.getLosses());

        statsRepository.save(stats);
        return stats;
    }

    @DeleteMapping("/{id}")
    public String deleteStats(@PathVariable Long id) {
        // Delete the stats entry
        statsRepository.deleteById(id);
        return success;
    }
}
