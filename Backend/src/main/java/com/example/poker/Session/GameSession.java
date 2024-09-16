package com.example.poker.Session;

import com.example.poker.Results.GameResult;
import com.example.poker.Users.User;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GameSession {

    //Game Session Table Values
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionID;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userId")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "game_session_players",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )

    //Stored as Separate Table in DB
    private List<User> players = new ArrayList<>();

    @OneToOne(mappedBy = "gameSession")
    private GameResult gameResult;


    //GamSession Getters and Setters

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


    public void clearPlayers() {
        players.clear();
    }

}
