package com.example.androidexample;

public class Pot {

    int potSize;

    public Pot(int potSize) {
        this.potSize = potSize;
    }

    public void addToPot(int amount){
        System.out.println("Added " + amount + " To The Pot.");
        potSize += amount;
    }

    public int getPotSize() {
        return potSize;
    }
}
