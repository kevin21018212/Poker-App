package com.example.androidexample;
import java.io.Serializable;

public class CurrentUser implements Serializable{

    String username;
    String password;

    public CurrentUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
