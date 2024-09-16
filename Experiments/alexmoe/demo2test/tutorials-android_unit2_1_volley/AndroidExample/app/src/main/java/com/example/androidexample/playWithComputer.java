package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Random;

public class playWithComputer extends AppCompatActivity{
    final String URL_STRING_REQ_Users = "http://coms-309-064.class.las.iastate.edu:8080/users/";

    //The user who logged in, aka the player in the game
    public CurrentUser currentUser;

    //Buttons
    private Button returnLobby, viewHands, callBtn, chkFoldBtn, raiseBtn, nextActionButton;

    //PLayer and Game Logic
    private Player player1, bot1, bot2, bot3, bot4;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> bots = new ArrayList<>();
    private ArrayList<Player> activePlayers = new ArrayList<>();
    private Pot pot;
    private CurrentBet currentBet;
    private CurrentBet tempBet;
    private int round = 1;
    private boolean isDealt = false;

    //Images for cards and player icons
    private ImageView flopCard1, flopCard2, flopCard3, turnCard, riverCard, player1hole1, player1hole2, player2hole1, player2hole2;
    private ImageView player3hole1, player3hole2,player4hole1, player4hole2, player5hole1, player5hole2;

    //Name and chip display areas for all players (player and bots), the last action, and size of the pot.
    private TextView player1name, bot1name, bot2name, bot3name, bot4name, player1chips, bot1chips, bot2chips, bot3chips, bot4chips, lastAction, potSize;

    //starting chip counts for bots
    private int bot1ChipCount = 500, bot2ChipCount = 500, bot3ChipCount = 500, bot4ChipCount = 500;

    //Name and chip count of the user
    public ArrayList<String> username = new ArrayList<>();
    public int playerChipCount;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_with_computer);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("currentUser")) {
            currentUser = (CurrentUser) intent.getSerializableExtra("currentUser");
        } else {
            System.out.println("no intent or no user");
        }

        username.add("user1");
        /**
         * !!!!!!!!!!!!!!!! Change once elicia does her mapping
         */
        playerChipCount = 500;

//button to view the order of hands in case they forget
        viewHands = findViewById(R.id.viewHands);

        returnLobby = findViewById(R.id.returnToHome);

        chkFoldBtn = findViewById(R.id.checkBtn);

        callBtn = findViewById(R.id.callBtn);

        raiseBtn = findViewById(R.id.raiseBtn);

        nextActionButton = findViewById(R.id.nextAction);
        /**
         * Cards for the flop to be revealed later
         */
        flopCard1 = findViewById(R.id.flopCard1);
        flopCard2 = findViewById(R.id.flopCard2);
        flopCard3 = findViewById(R.id.flopCard3);

        /**
         * Cards for the turn and river to be revealed later
         */
        turnCard = findViewById(R.id.turnCard);
        riverCard = findViewById(R.id.riverCard);

        /**
         * Texts for the last action display and size of the pot (integer value)
         */
        lastAction =  findViewById(R.id.lastActionText);
        potSize =  findViewById(R.id.potSize);

        /**
         * Hole cards for player1 (user) and the 4 bots
         */
        //Player 1 (main player)
        player1hole1 = findViewById(R.id.maincard1);
        player1hole2 = findViewById(R.id.maincard2);
        //Player 2
        player2hole1 = findViewById(R.id.player2hole1);
        player2hole2 = findViewById(R.id.player2hole2);
        //Player 3
        player3hole1 = findViewById(R.id.player3hole1);
        player3hole2 = findViewById(R.id.player3hole2);
        //Player 4
        player4hole1 = findViewById(R.id.player4hole1);
        player4hole2 = findViewById(R.id.player4hole2);
        //Player 5
        player5hole1 = findViewById(R.id.player5hole1);
        player5hole2 = findViewById(R.id.player5hole2);

        //Player 1 name and chips
        player1name =  findViewById(R.id.player1name);
        player1chips =  findViewById(R.id.player1chips);

        //Bot 1
        bot1name =  findViewById(R.id.bot1name);
        bot1chips =  findViewById(R.id.bot1chips);
        //Bot 2
        bot2name =  findViewById(R.id.bot2name);
        bot2chips =  findViewById(R.id.bot2chips);
        //Bot 3
        bot3name =  findViewById(R.id.bot3name);
        bot3chips =  findViewById(R.id.bot3chips);
        //Bot 4
        bot4name =  findViewById(R.id.bot4name);
        bot4chips =  findViewById(R.id.bot4chips);

        /**
         * The 5 community cards invisible
         */
        flopCard1.setVisibility(View.INVISIBLE);
        flopCard2.setVisibility(View.INVISIBLE);
        flopCard3.setVisibility(View.INVISIBLE);
        turnCard.setVisibility(View.INVISIBLE);
        riverCard.setVisibility(View.INVISIBLE);

        //go back to lobby waiting screen
        returnLobby.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        viewHands.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                popUp(viewHands);
            }
        });

        Deck deck = new Deck();

        callBtn.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                call(player1);

                //If player call is last thing to close the action
                if(roundComplete()){
                    System.out.println("line 192");
                    showTableCards(round, deck);
                } else {
                    disablePlayerButtons();
                    enableNextButton();
                }
            }
        });

        raiseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raise(player1);
                disablePlayerButtons();
                enableNextButton();
            }
        });

        chkFoldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFld(player1, 0);

                //If player check or fold is last to close the action
                if(roundComplete()){
                    System.out.println("line 215");
                    showTableCards(round, deck);
                } else {
                    disablePlayerButtons();
                    enableNextButton();
                }
            }
        });

        //Dealing and Player Info
        assignInfo();
        deck.shuffleDeck();
        assignCards(deck);

        startGame();
        firstTurn();

        nextActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBotTurns(0, deck);
                disableNextButton();
            }
        });
    }

    /**
     * Handles a round of bot decisions and bring it back to the player
     * @param deck
     */
    public void handleBotTurns(int currentBot, Deck deck) {
        final ArrayList<Player> players = new ArrayList<>(bots);

        if (currentBot < players.size()) {
            final Player currentPlayer = players.get(currentBot);

            if (!currentPlayer.isFolded()) {
                disablePlayerButtons();

                // Implement the logic for the current player's turn here
                if (!currentPlayer.hasMadeDecision()) {
                    if(roundComplete()){
                        System.out.println("line 262");
                        showTableCards(round, deck);
                        isDealt = true;
                    } else {
                        if(!checkForWinner()){ //returns false if not a winner,
                            botTurn(currentPlayer); // Handle the bot's turn
                        } else {
                            return;
                        }
                    }
                }

                // Introduce a delay after each player/bot's decision
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Proceed to the next player's turn after the delay
                        handleBotTurns(currentBot + 1, deck);
                    }
                }, 4000); // 4000 milliseconds = 4 seconds for the delay
            } else {
                // If the bot is folded, move to the next player/bot without delay
                handleBotTurns(currentBot + 1, deck);
            }
        } else {
            //once all bots have made a choice, go to player turn
            if(roundComplete() && !isDealt) {
                System.out.println("line 285");
                showTableCards(round, deck);
            }
            //check for winner before moving to player turn
            if(!checkForWinner()){
                System.out.println("Is there a winner?: " + checkForWinner());
                enablePlayerButtons();
                playerTurn();
            }

        }
    }
    /**
     * Starts the game logic sequence
     */
    public void startGame(){
        //create a pot of size 0
        pot = new Pot(0);
        currentBet = new CurrentBet(lastAction);
        tempBet = currentBet;
        lastAction.setText("Your Turn!\nRaise, Check, or Fold");
    }

    /**
     * Function for the turn of a bot, randomized decision
     * @param player
     */
    public void botTurn(Player player){
        if(!player.isFolded()){

            //If no bet
            if(currentBet.getSize() == 0){
                Random random = new Random();
                int randNum = random.nextInt(2);

                if(randNum == 0){
                    raise(player);
                }
                if(randNum == 1){
                    checkFld(player, 1);
                }

                //If there is already a bet
            } else {
                //If they have more than 0 chips
                if(player.getChips() != 0){
                    Random random = new Random();
                    int randNum = random.nextInt(3);

                    if(randNum == 0){
                        raise(player);
                    }
                    if(randNum == 1){
                        checkFld(player, 0);
                    }
                    if(randNum == 2){
                        call(player);
                    }
                } else {
                    //already a bet ahead of them, but they have zero chips (equivalent to a check)
                    checkFld(player, 1);
                }
            }
        }

    }

    /**
     * The first turn of the game. The player must choose whether to bet or check
     */
    public void firstTurn(){
        Toast.makeText(playWithComputer.this, "You Can Check or Raise", Toast.LENGTH_SHORT).show();

        raiseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raise(player1);
                disablePlayerButtons();
                nextActionButton.setEnabled(true);
            }
        });

        chkFoldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int decision;

                //If the amount needed to call is 0, then they can check, otherwise there is a bet and they have to fold
                if(currentBet.getToCall(player1) == 0){
                    decision = 1;
                } else {
                    decision = 0;
                }

                checkFld(player1, decision);
                disablePlayerButtons();
                nextActionButton.setEnabled(true);
            }
        });
    }

    /**
     * Options and logic for the turn of the human player
     */
    public void playerTurn(){
        lastAction.setText("Your turn!");
        enablePlayerButtons();

        if(!player1.isFolded()){
            if(currentBet.getSize() == 0 || pot.getPotSize() == 0){
                //there is not already a bet
                Toast.makeText(playWithComputer.this, "You Can Check or Raise", Toast.LENGTH_SHORT).show();
            } else {
                //there is already a bet
                Toast.makeText(playWithComputer.this, "You Can Call or Raise", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Checks if the round of betting is complete
     * @return
     */
    public boolean roundComplete() {
        Player raiser = null;
        int playersNotFolded = 0;
        int checkedPlayers = 0;

        for (Player player : players) {
            if (!player.isFolded()) {
                playersNotFolded++;
                if (player.getHasRaised()) {
                    raiser = player;
                } else if (player.getHasChecked()) {
                    checkedPlayers++;
                }
            }
        }

        if (raiser != null && playersNotFolded > 1) {
            for (Player player : players) {
                if (player != raiser && !player.hasMadeDecision() && !player.isFolded()) {
                    return false; // Someone other than the raiser hasn't made a decision
                }
            }
            System.out.println("Round complete");
            return true;
        }

        if (raiser == null && checkedPlayers == playersNotFolded && playersNotFolded > 1) {
            return true; // All players checked initially, but no one raised
        }

        return false; // The round is not yet complete
    }

    /**
     * function to determine which cards should be shown at what time
     * @param roundNum
     * @param deck
     */
    public void showTableCards(int roundNum, Deck deck){
        if(roundNum == 1){
            showFlopCards(deck);
        }
        if(roundNum == 2){
            showTurnCard(deck);
        }
        if(roundNum == 3){
            showRiverCard(deck);
        }
        //reset isDealt for next rounds
        isDealt = false;

        //increment round so it is ready for next call
        round += 1;

        //reset current bet for next round of betting
        /**
         * Here is start of error. The toCall variable of currentBet gets messed up causing players to add a negative
         * number of chips to the pot and therefore also have a negative number removed from their chip count (aka adding chips instead of removing them)
         */
        currentBet.resetBetRound();

        unsetPlayerDecisions();
        firstTurn();

        nextActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBotTurns(0, deck);
                disableNextButton();
            }
        });
    }

    /**
     * Shows the flop cards
     * @param deck
     */
    public void showFlopCards(Deck deck){
        System.out.println("Showing Flop Cards");
        int flopCard1Int = deck.dealCard();
        assignImage(flopCard1Int, flopCard1);
        flopCard1.setVisibility(View.VISIBLE);

        int flopCard2Int = deck.dealCard();
        assignImage(flopCard2Int, flopCard2);
        flopCard2.setVisibility(View.VISIBLE);

        int flopCard3Int = deck.dealCard();
        assignImage(flopCard3Int, flopCard3);
        flopCard3.setVisibility(View.VISIBLE);
    }

    /**
     * Reveals the turn card to players
     * @param deck
     */
    public void showTurnCard(Deck deck){
        int turnCardInt = deck.dealCard();
        assignImage(turnCardInt, turnCard);
        turnCard.setVisibility(View.VISIBLE);
    }

    /**
     * Reveals the river card to players
     * @param deck
     */
    public void showRiverCard(Deck deck){
        int riverCardInt = deck.dealCard();
        assignImage(riverCardInt, riverCard);
        riverCard.setVisibility(View.VISIBLE);
    }

    /**
     * Disables raise, call, chk/fold buttons from being pressed
     */
    public void disablePlayerButtons(){
        raiseBtn.setEnabled(false);
        callBtn.setEnabled(false);
        chkFoldBtn.setEnabled(false);
    }

    /**
     * Enables raise, call, chk/fold buttons from being pressed
     */
    public void enablePlayerButtons(){
        raiseBtn.setEnabled(true);
        callBtn.setEnabled(true);
        chkFoldBtn.setEnabled(true);
    }

    /**
     * Disables the next button (aka the bot turns)
     */
    public void disableNextButton(){
        nextActionButton.setEnabled(false);
    }

    /**
     * Enables the next button to be pressed
     */
    public void enableNextButton(){
        nextActionButton.setEnabled(true);
    }

    /**
     * Resets player decisions for each round of betting
     */
    public void unsetPlayerDecisions(){
        //Reset all bet tracking booleans for new round of betting
        for(Player playerTemp : players){
            playerTemp.setHasRaised(false);
            playerTemp.setHasChecked(false);
            playerTemp.setHasCalled(false);

            //If they are not folded (meaning they called), reset hasMadeDecision
            if(!playerTemp.isFolded()){
                playerTemp.setHasMadeDecision(false);
            }
        }
    }

    /**
     * Makes the cards of the param player darker upon folding
     * @param player
     */
    public void makeDarker(Player player){
        if (player == player1) {
            player1hole1.setAlpha(0.5f); // Change transparency to make it darker
            player1hole2.setAlpha(0.5f); // Change transparency to make it darker
        }
        if (player == bot1) {
            player2hole1.setAlpha(0.5f); // Change transparency to make it darker
            player2hole2.setAlpha(0.5f); // Change transparency to make it darker
        }
        if (player == bot2) {
            player3hole1.setAlpha(0.5f); // Change transparency to make it darker
            player3hole2.setAlpha(0.5f); // Change transparency to make it darker
        }
        if (player == bot3) {
            player4hole1.setAlpha(0.5f); // Change transparency to make it darker
            player4hole2.setAlpha(0.5f); // Change transparency to make it darker
        }
        if (player == bot4) {
            player5hole1.setAlpha(0.5f); // Change transparency to make it darker
            player5hole2.setAlpha(0.5f); // Change transparency to make it darker
        }

    }

    /**
     * Changes the display on screen after chips are added or removed
     * @param player
     */
    public void changePlayerChipCount(Player player){
        if (player == player1) {
            player1chips.setText(Integer.toString(player1.getChips()));
        }
        if (player == bot1) {
            bot1chips.setText(Integer.toString(bot1.getChips()));
        }
        if (player == bot2) {
            bot2chips.setText(Integer.toString(bot2.getChips()));
        }
        if (player == bot3) {
            bot3chips.setText(Integer.toString(bot3.getChips()));
        }
        if (player == bot4) {
            bot4chips.setText(Integer.toString(bot4.getChips()));
        }
    }

    /**
     * The function for a check or fold (depending on situation)
     * @param player
     * @param decision (0 if folding, 1 if checking)
     */
    public void checkFld(Player player, int decision){

        //Folding
        if(decision == 0){
            player.setFolded(true);
            activePlayers.remove(player);
            makeDarker(player);
            lastAction.setText(player.getName() + "\nHas folded");
            System.out.println(player.getName() + " Folded");

            //checking
        } else {
            player.setHasChecked(true);
            lastAction.setText(player.getName() + "\nHas Checked");
            System.out.println(player.getName() + " Checked");
        }
        player.setHasMadeDecision(true);
        player.setHasRaised(false);
        player.setHasCalled(false);
    }

    /**
     * The function for a call, aka when a player matches a previous player's bet
     * @param player
     */
    public void call(Player player){
        //Size of current bet
        int betSize = currentBet.getSize();

        //If player has 0 chips, they cannot call
        if(player.getChips() == 0){
            Toast.makeText(playWithComputer.this, "You cannot call, PLease Check or Fold", Toast.LENGTH_SHORT).show();
            playerTurn();
        }

        //If they will be all in
        if(betSize > player.getChips()){
            Toast.makeText(playWithComputer.this, player.getName() + "Is ALL IN!", Toast.LENGTH_SHORT).show();
        }

        // Calculate the amount needed to call
        int toCall = currentBet.getToCall(player);

        player.updateLastBet(toCall + player.getLastBet());
        player.removeChips(toCall);
        pot.addToPot(toCall);
        potSize.setText(Integer.toString(pot.getPotSize()));
        lastAction.setText(player.getName() + "\nHas Called");

        //update on-screen chip count for player
        changePlayerChipCount(player);
        player.setHasMadeDecision(true);
        player.setHasRaised(false);
        player.setHasCalled(true);
        player.setHasChecked(false);
        System.out.println(player.getName() + " Called");
    }

    /**
     * Function for a raise, aka when a player wants to increase the size of the bet
     * @param player
     */
    public void raise(Player player) {
        int currentBetSize = currentBet.getSize();
        int totalBet;
        int amount = 5;

        // Calculate the total bet amount including the current bet and the raise amount
        if (currentBetSize == 0) {
            // If there hasn't been a bet yet
            totalBet = amount;
        } else {
            // If there was a previous bet
            totalBet = currentBetSize + amount;
        }

        if (player.getChips() >= totalBet) {
            // Player has enough chips to make the bet
            currentBet.addToBet(totalBet - currentBetSize);
            lastAction.setText(player.getName() + "\nHas raised to: " + currentBet.getSize() + "!");

            pot.addToPot(totalBet);
            player.updateLastBet(totalBet);
            player.removeChips(totalBet);

            potSize.setText(Integer.toString(pot.getPotSize()));
            changePlayerChipCount(player);
            System.out.println(player.getName() + " Raised");

            player.setHasRaised(true);
            player.setHasCalled(false);
            player.setHasChecked(false);

            //If a player raises, undo the decision variable to allow them to make a new choice when the game gets back to their turn after someone behind them raised
            for(Player playerTemp : players){
                if(playerTemp.getHasCalled()){
                    playerTemp.setHasMadeDecision(false);
                    playerTemp.setHasRaised(false);
                }
            }
        } else {
            // Handle the case where the player doesn't have enough chips to raise
            Toast.makeText(playWithComputer.this, "You cannot raise, Please Check/Call", Toast.LENGTH_SHORT).show();
            playerTurn();
        }
    }

    /**
     * Checks for a winner due to all but one player being folded
     * @return
     */
    public boolean checkForWinner(){
        if (activePlayers.size() == 1) {
            Player winner = activePlayers.get(0); // The remaining player is the winner
            winner.addChips(pot.getPotSize()); //add chips won to total

            int winnerChipCount = winner.getChips();
            int winAmount; //net gain/loss of chips

            if(winner.getChips() >= 500){
                winAmount = winnerChipCount - 500; //if they won chips (ended with >500 )
            } else {
                winAmount = 500 - winnerChipCount; //if they ended with less than 500 chips (net loss)
            }

            lastAction.setText(winner.getName() + "\nHas Won");
            makeStringReqAddChips(winner.getName(), winAmount);
            Toast.makeText(playWithComputer.this, "Please return to home and start a new game!", Toast.LENGTH_LONG).show();
            return true;
            // Declare the winner, end the game, or perform any other necessary action
        }
        return false;
    }

    /**
     * Assigns Username and chip count to player and bots at the start (creation) of the game
     */
    public void assignInfo(){
        //Player name and chip assignments
        player1name.setText(currentUser.getUsername());
        player1chips.setText(Integer.toString(playerChipCount));

        //bot 1 name and chip assignment
        bot1name.setText("Bot 1");
        bot1chips.setText(Integer.toString(bot1ChipCount));

        //bot 2 name and chip assignment
        bot2name.setText("Bot 2");
        bot2chips.setText(Integer.toString(bot2ChipCount));

        //bot 3 name and chip assignment
        bot3name.setText("Bot 3");
        bot3chips.setText(Integer.toString(bot3ChipCount));

        //bot 4 name and chip assignment
        bot4name.setText("Bot 4");
        bot4chips.setText(Integer.toString(bot4ChipCount));
    }

    /**
     * Assigns cards to the player (face up/visible) and face down to the bots
     */
    public void assignCards(Deck deck){

        /**
         * Deal cards to main player
         */
        int card1 = deck.dealCard();
        int card2 = deck.dealCard();
        String cardName1 = deck.getCardName(card1);
        String cardName2 = deck.getCardName(card2);

        //Main player (user) is created
        player1 = new Player(currentUser.getUsername(), playerChipCount, cardName1, cardName2);
        players.add(player1);
        activePlayers.add(player1);

        assignImage(card1, player1hole1);
        assignImage(card2, player1hole2);
        player1hole1.setVisibility(View.VISIBLE);
        player1hole2.setVisibility(View.VISIBLE);

        /**
         * Deal card to Bots
         */
        //Bot 1 Cards
        player2hole1.setImageResource(R.drawable.card_back);
        player2hole1.setVisibility(View.VISIBLE);

        player2hole2.setImageResource(R.drawable.card_back);
        player2hole2.setVisibility(View.VISIBLE);
        int card3 = deck.dealCard();
        int card4 = deck.dealCard();
        String cardName3 = deck.getCardName(card3);
        String cardName4 = deck.getCardName(card4);

        bot1 = new Player("Bot 1", bot1ChipCount, cardName3, cardName4);
        players.add(bot1);
        bots.add(bot1);
        activePlayers.add(bot1);

        //Bot 2 Cards
        player3hole1.setImageResource(R.drawable.card_back);
        player3hole1.setVisibility(View.VISIBLE);

        player3hole2.setImageResource(R.drawable.card_back);
        player3hole2.setVisibility(View.VISIBLE);
        int card5 = deck.dealCard();
        int card6 = deck.dealCard();
        String cardName5 = deck.getCardName(card5);
        String cardName6 = deck.getCardName(card6);

        bot2 = new Player("Bot 2", bot2ChipCount, cardName5, cardName6);
        players.add(bot2);
        bots.add(bot2);
        activePlayers.add(bot2);

        //Bot 3 cards
        player4hole1.setImageResource(R.drawable.card_back);
        player4hole1.setVisibility(View.VISIBLE);

        player4hole2.setImageResource(R.drawable.card_back);
        player4hole2.setVisibility(View.VISIBLE);
        int card7 = deck.dealCard();
        int card8 = deck.dealCard();
        String cardName7 = deck.getCardName(card7);
        String cardName8 = deck.getCardName(card8);

        bot3 = new Player("Bot 3", bot3ChipCount, cardName7, cardName8);
        players.add(bot3);
        bots.add(bot3);
        activePlayers.add(bot3);

        //Bot 4 cards
        player5hole1.setImageResource(R.drawable.card_back);
        player5hole1.setVisibility(View.VISIBLE);

        player5hole2.setImageResource(R.drawable.card_back);
        player5hole2.setVisibility(View.VISIBLE);
        int card9 = deck.dealCard();
        int card10 = deck.dealCard();
        String cardName9 = deck.getCardName(card9);
        String cardName10 = deck.getCardName(card10);

        bot4 = new Player("Bot 4", bot4ChipCount, cardName9, cardName10);
        players.add(bot4);
        bots.add(bot4);
        activePlayers.add(bot4);
    }

    /**
     *
     * @param card
     * @param image
     *
     * Assigning all images to the proper cards based on the images
     */
    public void assignImage(int card, ImageView image){
        switch (card){
            case 1:
                image.setImageResource(R.drawable.ace_of_hearts);
                break;
            case 2:
                image.setImageResource(R.drawable.two_of_hearts);
                break;
            case 3:
                image.setImageResource(R.drawable.three_of_hearts);
                break;
            case 4:
                image.setImageResource(R.drawable.four_of_hearts);
                break;
            case 5:
                image.setImageResource(R.drawable.five_of_hearts);
                break;
            case 6:
                image.setImageResource(R.drawable.six_of_hearts);
                break;
            case 7:
                image.setImageResource(R.drawable.seven_of_hearts);
                break;
            case 8:
                image.setImageResource(R.drawable.eight_of_hearts);
                break;
            case 9:
                image.setImageResource(R.drawable.nine_of_hearts);
                break;
            case 10:
                image.setImageResource(R.drawable.ten_of_hearts);
                break;
            case 11:
                image.setImageResource(R.drawable.jack_of_hearts2);
                break;
            case 12:
                image.setImageResource(R.drawable.queen_of_hearts2);
                break;
            case 13:
                image.setImageResource(R.drawable.king_of_hearts2);
                break;
            //start clubs
            case 14:
                image.setImageResource(R.drawable.ace_of_clubs);
                break;
            case 15:
                image.setImageResource(R.drawable.two_of_clubs);
                break;
            case 16:
                image.setImageResource(R.drawable.three_of_clubs);
                break;
            case 17:
                image.setImageResource(R.drawable.four_of_clubs);
                break;
            case 18:
                image.setImageResource(R.drawable.five_of_clubs);
                break;
            case 19:
                image.setImageResource(R.drawable.six_of_clubs);
                break;
            case 20:
                image.setImageResource(R.drawable.seven_of_clubs);
                break;
            case 21:
                image.setImageResource(R.drawable.eight_of_clubs);
                break;
            case 22:
                image.setImageResource(R.drawable.nine_of_clubs);
                break;
            case 23:
                image.setImageResource(R.drawable.ten_of_clubs);
                break;
            case 24:
                image.setImageResource(R.drawable.jack_of_clubs2);
                break;
            case 25:
                image.setImageResource(R.drawable.queen_of_clubs2);
                break;
            case 26:
                image.setImageResource(R.drawable.king_of_clubs2);
                break;
            //end clubs
            //start diamonds
            case 27:
                image.setImageResource(R.drawable.ace_of_diamonds);
                break;
            case 28:
                image.setImageResource(R.drawable.two_of_diamonds);
                break;
            case 29:
                image.setImageResource(R.drawable.three_of_diamonds);
                break;
            case 30:
                image.setImageResource(R.drawable.four_of_diamonds);
                break;
            case 31:
                image.setImageResource(R.drawable.five_of_diamonds);
                break;
            case 32:
                image.setImageResource(R.drawable.six_of_diamonds);
                break;
            case 33:
                image.setImageResource(R.drawable.seven_of_diamonds);
                break;
            case 34:
                image.setImageResource(R.drawable.eight_of_diamonds);
                break;
            case 35:
                image.setImageResource(R.drawable.nine_of_diamonds);
                break;
            case 36:
                image.setImageResource(R.drawable.ten_of_diamonds);
                break;
            case 37:
                image.setImageResource(R.drawable.jack_of_diamonds2);
                break;
            case 38:
                image.setImageResource(R.drawable.queen_of_diamonds2);
                break;
            case 39:
                image.setImageResource(R.drawable.king_of_diamonds2);
                break;
            //end diamonds
            //start spades
            case 40:
                image.setImageResource(R.drawable.ace_of_spades);
                break;
            case 41:
                image.setImageResource(R.drawable.two_of_spades);
                break;
            case 42:
                image.setImageResource(R.drawable.three_of_spades);
                break;
            case 43:
                image.setImageResource(R.drawable.four_of_spades);
                break;
            case 44:
                image.setImageResource(R.drawable.five_of_spades);
                break;
            case 45:
                image.setImageResource(R.drawable.six_of_spades);
                break;
            case 46:
                image.setImageResource(R.drawable.seven_of_spades);
                break;
            case 47:
                image.setImageResource(R.drawable.eight_of_spades);
                break;
            case 48:
                image.setImageResource(R.drawable.nine_of_spades);
                break;
            case 49:
                image.setImageResource(R.drawable.ten_of_spades);
                break;
            case 50:
                image.setImageResource(R.drawable.jack_of_spades2);
                break;
            case 51:
                image.setImageResource(R.drawable.queen_of_spades2);
                break;
            case 52:
                image.setImageResource(R.drawable.king_of_spades2);
                break;
        }
    }

    /**
     *
     * @param view
     *
     * Code for the popup window of the hand rankings
     */
    public void popUp(View view){
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View viewPopupWindow = layoutInflater.inflate(R.layout.hand_rankings, null);

        PopupWindow popupWindow = new PopupWindow(viewPopupWindow, 1200, 1000, true);

        popupWindow.showAtLocation(view, Gravity.TOP, 0, 0);
    }

    /**
     * String req to get the chip count of the current user (aka whoever logged in)
     * @param username
     */
    public void makeStringReqAddChips(String username, int winAmount) {
        //manually create and add params to get url request string
        String s = URL_STRING_REQ_Users + "/addChips?username="+username+"&chips=" + winAmount;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                s,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the successful response here
                        Log.d("Chip Count Updated", response);

                        Toast.makeText(playWithComputer.this, "Your Chips Have Been Added", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    /**
                     *
                     * @param error
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("Error Adding Chips", error.toString());
                    }
                }
        ) {
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}