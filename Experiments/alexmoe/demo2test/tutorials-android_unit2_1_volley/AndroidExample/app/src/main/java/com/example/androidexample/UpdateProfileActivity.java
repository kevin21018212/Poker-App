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

public class UpdateProfileActivity extends AppCompatActivity {

    final String URL_STRING_REQ = "http://coms-309-064.class.las.iastate.edu:8080/users/";

    private Button backToLogin, submitBtn;

    private EditText newUsername, newPassword, newEmail, newId;

    private TextView userText4;
    public CurrentUser currentUser;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);

        Intent intent = getIntent();
        //no extra?
        if(intent != null && intent.hasExtra("currentUser")) {
            currentUser = (CurrentUser) intent.getSerializableExtra("currentUser");
        } else {
            System.out.println("no intent or no user");
        }

        userText4 = (TextView) findViewById(R.id.userText4);

        String name = currentUser.getUsername();
        userText4.setText(name);

        backToLogin = (Button) findViewById(R.id.homeBtn);
        submitBtn = (Button) findViewById(R.id.submitBtn);

        backToLogin.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                newUsername = findViewById(R.id.newUsername);
                newPassword = findViewById(R.id.newPassword);
                newEmail = findViewById(R.id.newEmail);
                newId = findViewById(R.id.newId);

                //These two lines get whatever has been entered into the user/pass inputs
                /**
                 * Blank strings to read user inupt for what they want their new info to be
                 */
                String newUser = newUsername.getText().toString();
                String newPass = newPassword.getText().toString();
                String newEm = newEmail.getText().toString();
                String newIDString = newId.getText().toString();
                String jsonString = "";

                if (isInteger(newIDString)) {
                    Toast.makeText(UpdateProfileActivity.this, "Updating User Info", Toast.LENGTH_SHORT).show();

                    try {
                        /**
                         * Json object for each portion of the json string for a user
                         */
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("userId", newIDString);
                        jsonObject.put("username", newUser);
                        jsonObject.put("email", newEm);
                        jsonObject.put("password", newPass);
                        jsonObject.put("chips", 1000);
                        jsonObject.put("card1", 0);
                        jsonObject.put("card2", 0);
                        jsonObject.put("sessionId", JSONObject.NULL);

                        jsonString = jsonObject.toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    makeStringReq(newIDString, jsonString);
                } else {
                    Toast.makeText(UpdateProfileActivity.this, "Your User ID Should Be An Integer", Toast.LENGTH_SHORT).show();
                }
            }

            /**
             *
             * @param s
             * @return T/F if the param is an integer
             */
            public boolean isInteger(String s){
                try{
                    int d = Integer.parseInt(s);
                } catch (NumberFormatException nfe){
                    return false;
                }
                return true;
            }

            public void makeStringReq(String userID, String requestBody) {
                //manually create and add params to get url request string
                String s = URL_STRING_REQ + userID;

                StringRequest stringRequest = new StringRequest(
                        Request.Method.PUT,
                        s,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                int chips;
                                // Handle the successful response here
                                Log.d("New User created", response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle any errors that occur during the request
                                Log.e("Error Creating User", error.toString());
                            }
                        }
                ) {
                    @Override
                    public byte[] getBody() {
                        return requestBody.getBytes(); // Convert your request body to byte array
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json"; // Set the content type of the request body
                    }
                };

                // Adding request to request queue
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }

        });
    }
}
