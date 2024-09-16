package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//import org.java_websocket.handshake.ServerHandshake;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class joinBotLobby extends AppCompatActivity {

     private Button showPlayers2;
     private TextView msgResponse;

     public CurrentUser currentUser;

     public int arrLength;

    private static final String URL_STRING_REQ = "http://coms-309-064.class.las.iastate.edu:8080/lobby/players";

    /**
     * On creation of the new screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_screen);

        Intent intent = getIntent();
        //no extra?
        if(intent != null && intent.hasExtra("currentUser")) {
            currentUser = (CurrentUser) intent.getSerializableExtra("currentUser");
        } else {
            System.out.println("no intent or no user");
        }

        TextView strMessage = findViewById(R.id.msgResponse);

        Button startGame = findViewById(R.id.startGame);
        Button menuBtn = findViewById(R.id.menuBtn);
        Button leaveLobby = findViewById(R.id.leaveLobby);
        Button showPlayers2 = findViewById(R.id.showPlayers2);
        msgResponse = (TextView) findViewById(R.id.msgResponse);

        /*
         * Button to go to main playing screen of poker table.
         */
        startGame.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {

                //If there are at least 2 players in lobby, game can be started
                if(arrLength >= 2){
                    Intent intent = new Intent(joinBotLobby.this, playWithPlayers.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else {
                    Toast.makeText(joinBotLobby.this, "Not Enough Players In Lobby", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /*
         * Returns back to the previous screen (button in top left).
         * In this case, previous screen is home page to join lobbies or edit profile
         */
        menuBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * goes back to previous screen
             * @param v
             */
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        leaveLobby.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        showPlayers2.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                 makeStringReqLobbyPlayers();
            }
        });

    }

    /**
     * String request to get the usernames of those in the lobby
     */
    private void makeStringReqLobbyPlayers () {

            //String s = URL_STRING_REQ + userID;
            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    URL_STRING_REQ,
                    new Response.Listener<String>() {
                        /**
                         *
                         * @param response
                         */
                        @Override
                        public void onResponse(String response) {
                            // Handle the successful response here
                            Log.d("Volley Response", response);
                            msgResponse.setText("");

                            JSONArray responseArr;
                            JSONObject object;

                            //set response as JSON
                            try {
                                responseArr = new JSONArray(response);
                                arrLength = responseArr.length();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            for (int i = 0; i < arrLength; i++) {
                                //put the object at array index i into JSONObject
                                try {
                                    object = responseArr.getJSONObject(i);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                try {
                                    String name = object.getString("username");
                                    msgResponse.append(("\n" + name));
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                Log.d("Volley Response", response);
                            }
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

    /**
     * String request to leave the lobby
     * @param username
     */
    private void makeStringReqLeave (String username) {
        String s = "http://coms-309-064.class.las.iastate.edu:8080/lobby/leave-lobby?username=" + username;
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

                        Toast.makeText(joinBotLobby.this, "Lobby Left", Toast.LENGTH_SHORT).show();
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

    }

