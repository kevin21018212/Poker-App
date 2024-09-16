package onetoone.Users;

import onetoone.PlayerStats.Stats;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // User ID

    private String username;
    private String email;
    private String password;
    private int chips;
    private List<String> usernames = new ArrayList<String>();
    private List<String> passwords = new ArrayList<String>();

    @OneToOne()
    @JoinColumn(name = "userId")
    private Stats stats;

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
        usernames.add(username);
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
        passwords.add(password);
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public String checkCredentials(String username, String password){
        if(usernames.contains(username)){
            if(passwords.contains(password)){
                return "true";
            }
        }
        return "false";
    }
}
