package onetoone.Users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // User ID

    private String username;
    private String email;
    private String password;
    private int chips;
    private short card1;
    private short card2;

    //user goes to stats
    //private Stats stats;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public short getCard1(){ return card1;}

    public void setCard1(short card1){ this.card1 = card1;}

    public short getCard2(){return card2;}

    public void setCard2(short card2){ this.card2 = card2;}
}
