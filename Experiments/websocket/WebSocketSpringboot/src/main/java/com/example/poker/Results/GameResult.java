package com.example.poker.Results;

import com.example.poker.Users.User;
import com.example.poker.Session.GameSession;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

@Entity
public class GameResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameResultID;

    @OneToOne
    @JoinColumn(name = "sessionID", referencedColumnName = "sessionID")
    @ApiModelProperty(notes = "Gamesession", name = "Gamesession", required = true)
    private GameSession gameSession;

    @OneToOne
    @JoinColumn(name = "winnerUserID", referencedColumnName = "userId")
    @ApiModelProperty(notes = "Winning User", name = "winnerUser", required = true)
    private User winnerUser;

    @ManyToMany
    @JoinTable(
            name = "game_result_losers",
            joinColumns = @JoinColumn(name = "gameResultID"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private List<User> loserUsers;

    public Long getGameResultID() {
        return gameResultID;
    }

    public void setGameResultID(Long gameResultID) {
        this.gameResultID = gameResultID;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public User getWinnerUser() {
        return winnerUser;
    }

    public void setWinnerUser(User winnerUser) {
        this.winnerUser = winnerUser;
    }

    public List<User> getLoserUsers() {
        return loserUsers;
    }

    public void setLoserUsers(List<User> loserUsers) {
        this.loserUsers = loserUsers;
    }
}
