package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewStatsActivity extends AppCompatActivity {

    final String URL_STRING_REQ = "http://coms-309-064.class.las.iastate.edu:8080/users/";

    private EditText userIDInput;

    public TextView strMessage;

    public CurrentUser currentUser;

    private TextView userText3;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_stats);

        Intent intent = getIntent();
        //no extra?
        if(intent != null && intent.hasExtra("currentUser")) {
            currentUser = (CurrentUser) intent.getSerializableExtra("currentUser");
        } else {
            System.out.println("no intent or no user");
        }

        userText3 = (TextView) findViewById(R.id.userText3);

        String name = currentUser.getUsername();
        userText3.setText(name);

        Button backBtn = findViewById(R.id.returnToHome);
        Button submitID = findViewById(R.id.submitID);
        Button updateProfileBtn = findViewById(R.id.editProfile);

        TextView strMessage = findViewById(R.id.returnedUser);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewStatsActivity.this, UpdateProfileActivity.class);
                intent.putExtra("currentUser", currentUser);
                startActivity(intent);

            }
        });

        submitID.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                //set the text entered by user (should be a number)
               userIDInput = findViewById(R.id.enterIDEdit);

               //String of input text
               String userID = userIDInput.getText().toString();

               if(isInteger(userID)){
                   Toast.makeText(ViewStatsActivity.this, "Fetching User Info", Toast.LENGTH_SHORT).show();
                   makeStringReq(userID);
               } else {
                   Toast.makeText(ViewStatsActivity.this, "Your User ID Should Be An Integer", Toast.LENGTH_SHORT).show();
               }

            }

            /**
             *
             * @param s
             * @return T/F is the param is an integer
             */
            public boolean isInteger(String s){
                try{
                    int d = Integer.parseInt(s);
                } catch (NumberFormatException nfe){
                    return false;
                }
                return true;
            }

            public void makeStringReq(String userID) {
                //manually create and add params to get url request string
                String s = URL_STRING_REQ + userID;

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
                                // Handle the successful response here
                                Log.d("Volley Response", response);

                                /**
                                 * Try to set each JSON object as a string to append to return string
                                 */
                                try {
                                    jObj = new JSONObject(response);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            //username
                                try {
                                    user = jObj.getString("username");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            //password
                                try {
                                    pass = jObj.getString("password");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            //email
                                try {
                                    email = jObj.getString("email");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            //chips
                                try {
                                    chips = jObj.getInt("chips");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                strMessage.setText("Username: " + user);
                                strMessage.append("\nPassword: " + pass);
                                strMessage.append("\nEmail: " + email);
                                strMessage.append("\nChip Count: " + chips);
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

        });


    }
}
