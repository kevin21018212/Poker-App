package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class homePageActivity extends AppCompatActivity {

    private Button viewStats, homeBtn, deleteUser, playBots, playPlayers;

    public CurrentUser currentUser;

    private TextView userText;


    private static final String URL_STRING_REQ = "http://10.90.72.79:8080/users/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        //no extra?
        if(intent != null && intent.hasExtra("currentUser")) {
            currentUser = (CurrentUser) intent.getSerializableExtra("currentUser");
        } else {
            System.out.println("no intent or no user");
        }
        userText = (TextView) findViewById(R.id.userText);

       String name = currentUser.getUsername();
          userText.setText(name);

        //stats button
        viewStats = (Button) findViewById(R.id.viewStats);

        //return to login screen
        homeBtn = (Button) findViewById(R.id.returnToLogin);

        //button to go to stats page
        deleteUser = (Button) findViewById(R.id.deleteUser);

        playPlayers = (Button) findViewById(R.id.playPlayers);
        playBots = (Button) findViewById(R.id.playBots);

        //go to view stats page
        viewStats.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homePageActivity.this, ViewStatsActivity.class);
                intent.putExtra("currentUser", currentUser);
                startActivity(intent);
            }
        });

        //Go back to Login Page
        homeBtn.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        deleteUser.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homePageActivity.this, deleteProfileActivity.class);
                intent.putExtra("currentUser", currentUser);
                startActivity(intent);
            }
        });

        playPlayers.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                makeStringReqJoin(currentUser.getUsername());

                Intent intent = new Intent(homePageActivity.this, joinBotLobby.class);
                intent.putExtra("currentUser", currentUser);
                startActivity(intent);
            }
        });

        playBots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homePageActivity.this, playWithComputer.class);
                intent.putExtra("currentUser", currentUser);
                startActivity(intent);
            }
        });
    }

    /**
     * String request to join the lobby for playing with PLayers
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

                        Toast.makeText(homePageActivity.this, "Joining Lobby", Toast.LENGTH_SHORT).show();
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