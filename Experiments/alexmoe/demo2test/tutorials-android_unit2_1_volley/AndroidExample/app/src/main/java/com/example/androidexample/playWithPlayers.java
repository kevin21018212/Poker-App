package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class playWithPlayers extends AppCompatActivity {

    final String URL_STRING_REQ_Lobby = "http://coms-309-064.class.las.iastate.edu:8080/lobby";

    final String URL_STRING_REQ_game = "http://coms-309-064.class.las.iastate.edu:8080/";

    public CurrentUser currentUser;
    private Button returnLobby, viewHands, callBtn, chkFoldBtn, raiseBtn;

    private Player player1, player2, player3, player4, player5;

    private ImageView flopCard1, flopCard2, flopCard3, turnCard, riverCard, player1hole1, player1hole2, player2hole1, player2hole2;
    private ImageView player3hole1, player3hole2,player4hole1, player4hole2, player5hole1, player5hole2;

    private TextView player1name, player2name, player3name, player4name, player5name;

    public ArrayList<String> usernames = new ArrayList<>();
    public ArrayList<Integer> chipCounts = new ArrayList<>();

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_with_players);

        Intent intent = getIntent();
        //no extra?
        if(intent != null && intent.hasExtra("currentUser")) {
            currentUser = (CurrentUser) intent.getSerializableExtra("currentUser");
        } else {
            System.out.println("no intent or no user");
        }

//button to view the order of hands in case they forget
        viewHands = (Button) findViewById(R.id.viewHands);

        returnLobby = (Button) findViewById(R.id.returnToHome);

        chkFoldBtn = (Button) findViewById(R.id.checkBtn);

        callBtn = (Button) findViewById(R.id.callBtn);

        raiseBtn = (Button) findViewById(R.id.raiseBtn);
        /**
         * Cards for the flop to be revealed later
         */
        flopCard1 = (ImageView) findViewById(R.id.flopCard1);
        flopCard2 = (ImageView) findViewById(R.id.flopCard2);
        flopCard3 = (ImageView) findViewById(R.id.flopCard3);

        /**
         * Cards for the turn and river to be revealed later
         */
        turnCard = (ImageView) findViewById(R.id.turnCard);
        riverCard = (ImageView) findViewById(R.id.riverCard);
        /**
         * Hole cards for players 1-5. Revealed only after the game
         */
    //Player 1 (main player)
        player1hole1 = (ImageView) findViewById(R.id.maincard1);
        player1hole2 = (ImageView) findViewById(R.id.maincard2);
    //Player 2
        player2hole1 = (ImageView) findViewById(R.id.player2hole1);
        player2hole2 = (ImageView) findViewById(R.id.player2hole2);
    //Player 3
        player3hole1 = (ImageView) findViewById(R.id.player3hole1);
        player3hole2 = (ImageView) findViewById(R.id.player3hole2);
    //Player 4
        player4hole1 = (ImageView) findViewById(R.id.player4hole1);
        player4hole2 = (ImageView) findViewById(R.id.player4hole2);
    //Player 5
        player5hole1 = (ImageView) findViewById(R.id.player5hole1);
        player5hole2 = (ImageView) findViewById(R.id.player5hole2);

        player1name = (TextView) findViewById(R.id.player1name);
        player2name = (TextView) findViewById(R.id.player2name);
        player3name = (TextView) findViewById(R.id.player3name);
        player4name = (TextView) findViewById(R.id.player4name);
        player5name = (TextView) findViewById(R.id.player5name);

        /**
         * The 5 community cards invisible
         */
        flopCard1.setVisibility(View.INVISIBLE);
        flopCard2.setVisibility(View.INVISIBLE);
        flopCard3.setVisibility(View.INVISIBLE);
        turnCard.setVisibility(View.INVISIBLE);
        riverCard.setVisibility(View.INVISIBLE);

        /**
         * The cards of each player set to invisible
         */
        player1hole1.setVisibility(View.INVISIBLE);
        player1hole2.setVisibility(View.INVISIBLE);
        player2hole1.setVisibility(View.INVISIBLE);
        player2hole2.setVisibility(View.INVISIBLE);
        player3hole1.setVisibility(View.INVISIBLE);
        player3hole2.setVisibility(View.INVISIBLE);
        player4hole1.setVisibility(View.INVISIBLE);
        player4hole2.setVisibility(View.INVISIBLE);
        player5hole1.setVisibility(View.INVISIBLE);
        player5hole2.setVisibility(View.INVISIBLE);

        //go back to lobby waiting screen
        returnLobby.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                makeStringReqJoin(currentUser.getUsername());
                onBackPressed();
            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                return;
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

        makeStringReqLobbyPlayers();

    }

    public void assignInfo(int count){
        //set player names on screen
        if(count >= 5){
            player5name.setText(usernames.get(4));
            player5name.append("\n" + chipCounts.get(4));
        }
        if (count >= 4 ){
            player4name.setText(usernames.get(3));
            player4name.append("\n" + chipCounts.get(3));
        }
        if (count >= 3 ){
            player3name.setText(usernames.get(2));
            player3name.append("\n" + chipCounts.get(2));
        }
        if (count >= 2 ){
            player2name.setText(usernames.get(1));
            player2name.append("\n" + chipCounts.get(1));
        }
        if (count >= 1){
            player1name.setText(usernames.get(0));
            player1name.append("\n" + chipCounts.get(0));
        } else {
            System.out.println("No players in Lobby");
        }
    }

    /**
     * Assigns cards to each player in the lobby (and therefore the game)
     * @param playerCount
     */
    public void assignCards(int playerCount){
        Deck deck = new Deck();
        deck.shuffleDeck();

        if(playerCount < 2){
            System.out.println("Not enough Players in Lobby");
        }

        /**
         * Deal cards to at least two players since that is minimum for a game
         */
        if (playerCount >= 2) {
            //Player 1 creation
            player1hole1.setImageResource(R.drawable.card_back);
            player1hole1.setVisibility(View.VISIBLE);

            player1hole2.setImageResource(R.drawable.card_back);
            player1hole2.setVisibility(View.VISIBLE);
            int card1 = deck.dealCard();
            int card2 = deck.dealCard();
            String cardName1 = deck.getCardName(card1);
            String cardName2 = deck.getCardName(card2);

            player1 = new Player(usernames.get(0), chipCounts.get(0), cardName1, cardName2);

            //PLayer 2 creation
            player2hole1.setImageResource(R.drawable.card_back);
            player2hole1.setVisibility(View.VISIBLE);

            player2hole2.setImageResource(R.drawable.card_back);
            player2hole2.setVisibility(View.VISIBLE);
            int card3 = deck.dealCard();
            int card4 = deck.dealCard();
            String cardName3 = deck.getCardName(card3);
            String cardName4 = deck.getCardName(card4);

            player2 = new Player(usernames.get(1), chipCounts.get(1), cardName3, cardName4);

            //If player 1 is the one who logged in, show their cards
            if(usernames.get(0).equals(currentUser.getUsername())){
                assignImage(card1, player1hole1);
                assignImage(card2, player1hole2);
                System.out.println(cardName1);
                System.out.println(cardName2);
            }

            //If player 2 is the one who logged in, show their cards
            if(usernames.get(1).equals(currentUser.getUsername())){
                assignImage(card3, player2hole1);
                assignImage(card4, player2hole2);
                System.out.println(cardName3);
                System.out.println(cardName4);
            }

        }

        if (playerCount >= 3){
            //Deal player three cards
            player3hole1.setImageResource(R.drawable.card_back);
            player3hole1.setVisibility(View.VISIBLE);

            player3hole2.setImageResource(R.drawable.card_back);
            player3hole2.setVisibility(View.VISIBLE);
            int card5 = deck.dealCard();
            int card6 = deck.dealCard();
            String cardName5 = deck.getCardName(card5);
            String cardName6 = deck.getCardName(card6);

            player3 = new Player(usernames.get(2), chipCounts.get(2), cardName5, cardName6);

            if(usernames.get(2).equals(currentUser.getUsername())){
                assignImage(card5, player2hole1);
                assignImage(card6, player2hole2);
            }
        }

        if (playerCount >= 4){
            //Deal player four cards
            player4hole1.setImageResource(R.drawable.card_back);
            player4hole1.setVisibility(View.VISIBLE);

            player4hole2.setImageResource(R.drawable.card_back);
            player4hole2.setVisibility(View.VISIBLE);
            int card7 = deck.dealCard();
            int card8 = deck.dealCard();
            String cardName7 = deck.getCardName(card7);
            String cardName8 = deck.getCardName(card8);

            player4 = new Player(usernames.get(3), chipCounts.get(3), cardName7, cardName8);

            if(usernames.get(3).equals(currentUser.getUsername())){
                assignImage(card7, player2hole1);
                assignImage(card8, player2hole2);
            }
        }

        if (playerCount >= 5){
            //Deal player five cards
            player5hole1.setImageResource(R.drawable.card_back);
            player5hole1.setVisibility(View.VISIBLE);

            player5hole2.setImageResource(R.drawable.card_back);
            player5hole2.setVisibility(View.VISIBLE);
            int card9 = deck.dealCard();
            int card10 = deck.dealCard();
            String cardName9 = deck.getCardName(card9);
            String cardName10 = deck.getCardName(card10);

            player5 = new Player(usernames.get(4), chipCounts.get(4), cardName9, cardName10);

            if(usernames.get(4).equals(currentUser.getUsername())){
                assignImage(card9, player2hole1);
                assignImage(card10, player2hole2);
            }
        }
    }

    /**
     * Makes a string req to the lobby websocket to get the players in the current lobby, get and display their info, and deal cards accordingly
     */
    public void makeStringReqLobbyPlayers() {
        //manually create and add params to get url request string
        String s = URL_STRING_REQ_Lobby + "/players";

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                s,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Handle the successful response here
                        Log.d("Player Retrieval Successful", response);

                        JSONArray responseArr;
                        JSONObject object;

                        //set response as JSON
                        try {
                            responseArr = new JSONArray(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        for(int i = 0; i < responseArr.length(); i++){
                            try {
                                object = responseArr.getJSONObject(i);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            try {
                                String name = object.getString("username");
                                usernames.add(name);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            try {
                                int chips = object.getInt("chips");
                                chipCounts.add(chips);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        int playerCount = usernames.size();
                        assignCards(playerCount);
                        assignInfo(playerCount);
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
                        Log.e("Volley Error", error.toString());
                    }
                }
        ) {
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    /**
     * String request to leave the lobby
     * @param username
     */
    private void makeStringReqJoin (String username) {
        String s = "http://coms-309-064.class.las.iastate.edu:8080/lobby/join-lobby?username=" + username;
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                s,
                new Response.Listener<String>() {
                    /**
                     *
                     * @param response
                     */
                    @Override
                    public void onResponse(String response) {
                        // Handle the successful response here
                        Log.d("Volley Response", response);

                        Toast.makeText(playWithPlayers.this, "Joining Lobby", Toast.LENGTH_SHORT).show();
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
                        Log.e("Volley Error", error.toString());
                    }
                }
        ) {
            /**
             *
             * @return
             */
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void makeStringReqGame(String userID) {
        //manually create and add params to get url request string
        String s = URL_STRING_REQ_game + userID;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                s,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jObj;
                        String user;
                        String pass;
                        String email;
                        int chips;

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
                        Log.e("Volley Error", error.toString());
                    }
                }
        ) {
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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
}
