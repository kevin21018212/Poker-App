package onetoone.PlayerStats;

import onetoone.Results.GameResult;
import onetoone.Users.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Stats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statsId; // Stats ID

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", foreignKey = @ForeignKey(name = "fk_user_stats"))
    private User user;

    private int gamesPlayed;
    private int wins;
    private int losses;
    private int totalChipsWon;
    private int totalChipsLost;

    public Long getStatsId() {
        return statsId;
    }

    public void setStatsId(Long statsId) {
        this.statsId = statsId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getTotalChipsWon() {
        return totalChipsWon;
    }

    public void setTotalChipsWon(int totalChipsWon) {
        this.totalChipsWon = totalChipsWon;
    }

    public int getTotalChipsLost() {
        return totalChipsLost;
    }

    public void setTotalChipsLost(int totalChipsLost) {
        this.totalChipsLost = totalChipsLost;
    }
}
