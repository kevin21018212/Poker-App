package com.example.poker.Session;

import com.example.poker.Results.GameResult;
import com.example.poker.Users.User;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionID;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userId")
    @ApiModelProperty(notes = "User", name = "User", required = true)
    private User user;

    @OneToMany(mappedBy = "gameSession")
    private List<User> players;


    @OneToOne(mappedBy = "gameSession")
    @ApiModelProperty(notes = "Game rankings", name = "GameResults", required = true)
    private GameResult gameResult;


    public Long getSessionID() {
        return sessionID;
    }

    public void setSessionID(Long sessionID) {
        this.sessionID = sessionID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getPlayers() {
        return players;
    }

    public void setPlayers(List<User> players) {
        this.players = players;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

    public void addPlayer(User player) {
        players.add(player);
    }
}
