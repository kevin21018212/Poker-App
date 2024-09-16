package com.example.poker.PlayerStats;

import com.example.poker.Users.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import io.swagger.annotations.ApiModelProperty;

@Entity
public class Stats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statsId;

    @ApiModelProperty(notes = "Games played by user", name = "gamesPlayed", required = false)
    private int gamesPlayed;
    @ApiModelProperty(notes = "User's wins", name = "User wins", required = true)
    private int wins;
    @ApiModelProperty(notes = "User's losses", name = "User losses", required = true)
    private int losses;
    @ApiModelProperty(notes = "User's total Chips won", name = "User Chips won", required = false)
    private int totalChipsWon;
    @ApiModelProperty(notes = "User's total Chips lost", name = "User chips lost", required = false)
    private int totalChipsLost;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", foreignKey = @ForeignKey(name = "fk_user_stats"))
    private User user;


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
