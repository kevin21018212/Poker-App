package com.example.poker.PlayerStats;

import com.example.poker.Users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
public class Stats {



    //Declares Values in Stats table in DB
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statsId;
    private int gamesPlayed;
    private int wins;
    private int losses;
    private int totalChipsWon;
    private int totalChipsLost;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "userId", referencedColumnName = "userId", foreignKey = @ForeignKey(name = "fk_user_stats"))
    private User user;



    //Stats Getters and Setters
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


}
