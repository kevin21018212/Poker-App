package com.example.poker.Users;

import com.example.poker.PlayerStats.Stats;
import com.example.poker.Session.GameSession;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // User ID
    @ApiModelProperty(notes = "User's username", name = "User username", required = true)
    @NotNull
    private String username;
    @ApiModelProperty(notes = "User's email", name = "User email", required = true)
    @NotNull
    private String email;
    @ApiModelProperty(notes = "User's password", name = "User password", required = true)
    @NotNull
    private String password;
    @ApiModelProperty(notes = "User's amount of chips", name = "User chips", required = true)
    @NotNull
    private int chips;
    @ApiModelProperty(notes = "User's card1", name = "User card1", required = true)
    @NotNull
    private int card1;
    @ApiModelProperty(notes = "User's card2", name = "User card2", required = true)
    @NotNull
    private int card2;



    @ManyToOne
    @JoinColumn(name = "sessionID", referencedColumnName = "sessionID")
    private GameSession gameSession;


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

    public int getCard1() {
        return card1;
    }

    public void setCard1(int card1) {
        this.card1 = card1;
    }

    public int getCard2() {
        return card2;
    }

    public void setCard2(int card2) {
        this.card2 = card2;
    }


    public Stats getStats() {

        return null;
    }


}
