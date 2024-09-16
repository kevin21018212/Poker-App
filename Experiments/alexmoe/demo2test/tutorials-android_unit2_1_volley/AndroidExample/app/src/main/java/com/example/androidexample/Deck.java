package com.example.androidexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class Deck {
    private ArrayList<Integer> cards;
    private Map<Integer, String> cardNames;

    public Deck() {
        cards = new ArrayList<>();
        cardNames = new HashMap<>();
//hearts
        for (int i = 1; i <= 13; i++) {
            cards.add(i);
            cardNames.put(i, getValueName(i) + "H");
        }

        // Clubs
        for (int i = 14; i <= 26; i++) {
            cards.add(i);
            cardNames.put(i, getValueName(i - 13) + "C");
        }

        // Diamonds
        for (int i = 27; i <= 39; i++) {
            cards.add(i);
            cardNames.put(i, getValueName(i - 26) + "D");
        }

        // Spades
        for (int i = 40; i <= 52; i++) {
            cards.add(i);
            cardNames.put(i, getValueName(i - 39) + "S");
        }

    }

    private String getValueName(int value) {
        switch (value) {
            case 1:
                return "1";
            case 11:
                return "11";
            case 12:
                return "12";
            case 13:
                return "13";
            default:
                return String.valueOf(value);
        }
    }

    public void shuffleDeck(){
        Collections.shuffle(cards);
    }

    public ArrayList<Integer> getCards() {
        return cards;
    }

    public Map<Integer, String> getCardNames() {
        return cardNames;
    }

    public String getCardName(int cardNumber) {
        return cardNames.get(cardNumber);
    }

    public int dealCard() {
        if (!cards.isEmpty()) {
            int card = cards.remove(1); // Remove the first card from the list
            return card;
        } else {
            // Handle case when no cards are left
            return -1; // Return a value to signify that no cards are left
        }
    }
}
