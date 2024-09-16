package com.example.androidexample;

import java.util.ArrayList;

public class Player {
    private String name;
    private String card1;
    private String card2;
    private int chips, lastBet;
    private boolean folded, hasMadeDecision, hasRaised, hasCalled, hasChecked;

    public Player(String name, int chips, String card1, String card2) {
        this.name = name;
        this.chips = chips;
        this.card1 = card1;
        this.card2 = card2;
        lastBet = 0;
        folded = false;
    }

    /**
     * Set the folded variable, set in fold method
     * @param folded
     */
    public void setFolded(boolean folded) {
        this.folded = folded;
    }

    /**
     * Gets if the player has folded or not. Used to determine if the player should be allowed to continue being in the hand
     * @return
     */
    public boolean isFolded(){
        return folded;
    }

    /**
     * Gets if the player has raised, used in determining end of round
     * @return
     */
    public boolean getHasRaised() {
        return hasRaised;
    }

    /**
     * Set the hasRaised variable, used in determining end of round
     * @param hasRaised
     */
    public void setHasRaised(boolean hasRaised){
        this.hasRaised = hasRaised;
    }

    /**
     * Gets if the player has called the highest bet so far
     * @return
     */
    public boolean getHasCalled() {
        return hasCalled;
    }

    /**
     * Sets the hasCalled variable if the player has matched the current bet
     * @param hasCalled
     */
    public void setHasCalled(boolean hasCalled){
        this.hasCalled = hasCalled;
    }

    public boolean getHasChecked() {
        return hasChecked;
    }

    public void setHasChecked(boolean hasChecked){
        this.hasChecked = hasChecked;
    }

    /**
     * Returns current number of chips the player has
     * @return
     */
    public int getChips() {
        return chips;
    }

    /**
     * Adds chips to player chip count
     * @param chipChange
     */
    public void addChips(int chipChange){
        if(!isFolded()){
            chips += chipChange;
        }
    }

    /**
     * Removes chips from player chip count
     * @param chipChange
     */
    public void removeChips(int chipChange){
        if(!isFolded()){
            chips -= chipChange;
        }
    }

    /**
     * Tracks the ampunt of the last bet of the player
     * @param amount
     */
    public void updateLastBet(int amount){
        lastBet += amount;
    }

    /**
     * Returns the size of the player's last bet
     * @return
     */
    public int getLastBet(){
        return lastBet;
    }

    public boolean hasMadeDecision(){
        return hasMadeDecision;
    }

    public void setHasMadeDecision(boolean bool){
        this.hasMadeDecision = bool;
    }

    public String getName() {
        return name;
    }

    public String getCard1() {
        return card1;
    }

    public String getCard2() {
        return card2;
    }
}
