package com.example.poker.Results;

import com.example.poker.Users.User;
import com.example.poker.Session.GameSession;
import javax.persistence.*;
import java.util.List;

@Entity
public class GameResult {

    //Game Result Table Values
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameResultID;

    @OneToOne
    @JoinColumn(name = "sessionID", referencedColumnName = "sessionID")
    private GameSession gameSession;

    @OneToOne
    @JoinColumn(name = "winnerUserID", referencedColumnName = "userId")
    private User winnerUser;

    @ManyToMany
    @JoinTable(
            name = "game_result_losers",
            joinColumns = @JoinColumn(name = "gameResultID"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    //Stored as a seperate table in DB
    private List<User> loserUsers;


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
