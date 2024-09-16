package onetoone.Results;

import onetoone.Session.GameSession;
import onetoone.Users.User;

import javax.persistence.*;

@Entity
public class GameResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameID;

    @ManyToOne
    @JoinColumn(name = "sessionID", referencedColumnName = "sessionID")
    private GameSession gameSession;

    @Column(name = "gameType")
    private String gameType;

    @ManyToOne
    @JoinColumn(name = "winnerUserID", referencedColumnName = "userId")
    private User winnerUser;

    @ManyToOne
    @JoinColumn(name = "loserUserID", referencedColumnName = "userId")
    private User loserUser;

    public Long getGameID() {
        return gameID;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public User getWinnerUser() {
        return winnerUser;
    }

    public void setWinnerUser(User winnerUser) {
        this.winnerUser = winnerUser;
    }

    public User getLoserUser() {
        return loserUser;
    }

    public void setLoserUser(User loserUser) {
        this.loserUser = loserUser;
    }
}
