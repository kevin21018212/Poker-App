package com.example.androidexample;

import android.widget.TextView;

public class CurrentBet {
    //Size of current bet
    int size;

    public CurrentBet(TextView potText){
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public void addToBet(int amount){
        size += amount;
    }

    public int getToCall(Player player) {
        int playerLastBet = player.getLastBet();
        int currentBetSize = getSize();

        if (playerLastBet == 0) {
            // Player has not placed a bet yet, so they need to match the current bet size
            return currentBetSize;
        } else {
            // Calculate the difference between the current bet and the player's last bet
            return currentBetSize - playerLastBet;
        }
    }

    public void resetBetRound(){
        size = 0;
    }
}
