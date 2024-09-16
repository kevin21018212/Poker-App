package com.example.poker.websockets.Game;


import com.example.poker.Session.GameSession;
import com.example.poker.Session.GameSessionService;
import com.example.poker.Users.User;
import com.example.poker.Users.UserRepository;
import com.example.poker.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class  GameService {

    private final List<User> gamePlayer = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameSessionService gameSessionService;



    public void performFold(Long gameSession, String username) {
        User  player = userRepository.findByUsername(username);

        if(player != null) {
            gamePlayer.remove(player);
        }
    }
    public void startGame( List<User> gameUsers){
        GameSession gameSession = new GameSession();
        gameSession.setPlayers(gameUsers);
        gameSession.setSessionID(Long.valueOf("2"));

    }

    public void performCheck(Long gameId, String username) {}

    public void performRaise(Long gameId, String username, int chips) {
        User player = userRepository.findUserByUsername(username);

            int newChips = player.getChips() - chips;
            if (newChips > 0) {
                player.setChips(10);
        }
            userRepository.save(player);
    }





    //Hand should look something like "1H 3S 6H 7C 9D 10S 11C"
    //wrote half of this at 5 am so its very messy but it works
    public int findRankOfHands(String hand){
        int rank1 = 1; //Royal Flush
        int rank2 = 2; //Straight Flush
        int rank3 = 3; //Four of a Kind
        int rank4 = 4; //FullHouse
        int rank5 = 5; //Flush
        int rank6 = 6; //Straight
        int rank7 = 7; //Three of a Kind
        int rank8 = 8; //Two Pair
        int rank9 = 9; //One Pair
        int rank10 = 10; //High Card

        int finalrank;
        int spadesCount = 0;
        int diamondCount = 0;
        int heartCount =0;
        int clubsCount =0;

        boolean flushPresent = false;
        boolean straightPresent = false;
        boolean pairPresent = false;
        boolean threeKindPresent = false;


        ArrayList<Integer> numbers = new ArrayList<Integer>();
        ArrayList<Character> suits = new ArrayList<Character>();
        //Parse out numbers
        for(int i = 0; i < hand.length() -1; i++){
            if ((hand.charAt(i) == '1') && ((hand.charAt(i +1) == '0') || (hand.charAt(i +1) == '1') || (hand.charAt(i +1) == '2') || (hand.charAt(i +1) == '3'))){
                String temp = "" + hand.charAt(i) + hand.charAt(i+1);
                numbers.add(Integer.valueOf(temp));
                i++;

            }
            else if((hand.charAt(i) == '1') || (hand.charAt(i) =='2') || (hand.charAt(i) =='3') || (hand.charAt(i) =='4') || (hand.charAt(i) =='5') || (hand.charAt(i) =='6') || (hand.charAt(i) =='7') || (hand.charAt(i) =='8') || (hand.charAt(i) =='9')){
            numbers.add(Character.getNumericValue(hand.charAt(i)));

            }
        }
        //Parse out Card types
        for(int i = 0; i < hand.length(); i++){
            if((hand.charAt(i) == 'S') || (hand.charAt(i) == 'D') || (hand.charAt(i) == 'C') || (hand.charAt(i) == 'H')){
            suits.add(hand.charAt(i));
            }
        }

        Collections.sort(numbers);

        //Check for High Card
        finalrank = rank10;

        //Check for One Pair
        int pairCount =1;
        int pairCompare = 0;
        for(int i =0; i< numbers.size() -1 ; i++) {
            for(int j =i +1; j<numbers.size(); j++) {
                int temp = numbers.get(i);

                if (numbers.get(i).equals(numbers.get(j))) {
                    pairCount++;
                }
            }
            if(pairCount > pairCompare){
                pairCompare = pairCount;
            }
            if ((pairCount == 2) && pairPresent) {
                finalrank = rank8;
            }else if(pairCount == 2 && !pairPresent){
                finalrank = rank9;
                pairPresent = true;
            }
            pairCount = 1;

        }

        if (pairCompare == 3) {
            finalrank = rank7;
            threeKindPresent = true;
        } else if (pairCompare == 4) {
            return finalrank = rank3;
        }


        //Check for Straight
        for(int i = 0; i<numbers.size() -2; i++){
            for(int j = 1; j < numbers.size() -1; j++){
                if(numbers.get(i) +1 == numbers.get(j)){
                    if((numbers.get(j) + 1 == numbers.get(j+1)) && (numbers.get(j+1) +1 == numbers.get(j+2)) && (numbers.get(j+2) +1 == numbers.get(j+3))){
                        finalrank = rank6;
                        straightPresent = true;
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }

        //Check for Flush
        for(int i = 0; i< suits.size(); i++){
            if(suits.get(i) == 'S'){
                spadesCount++;
            }
            if(suits.get(i) == 'H'){
                heartCount++;
            }
            if(suits.get(i) == 'C'){
                clubsCount++;
            }
            if(suits.get(i) == 'D'){
                diamondCount++;
            }
        }
        if((spadesCount == 5) || (heartCount == 5) || (clubsCount == 5) || (diamondCount == 5)){
            flushPresent = true;
        }
        if(flushPresent){
            finalrank = rank5;
        }

        //Check for FullHouse
        if(threeKindPresent && pairPresent){
            finalrank = rank4;
        }
        //Check for Straight Flush
        if(flushPresent && straightPresent){
        finalrank = rank2;
        }
        //Check for Royal Flush
        if(flushPresent){
            if(numbers.contains(13) && numbers.contains(1) && numbers.contains(12) && numbers.contains(11) && numbers.contains(10)){
                finalrank = rank1;
            }
        }
         return finalrank;

    }

}

