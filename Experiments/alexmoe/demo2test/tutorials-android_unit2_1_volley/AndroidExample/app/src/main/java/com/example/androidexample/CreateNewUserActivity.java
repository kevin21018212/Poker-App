package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateNewUserActivity extends AppCompatActivity{

    final String URL_STRING_REQ = "http://coms-309-064.class.las.iastate.edu:8080/users/";

    private Button backToLogin, submitBtn;

    private EditText newUsername, newPassword, newEmail, newId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_user);

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
                String newUser = newUsername.getText().toString();
                String newPass = newPassword.getText().toString();
                String newEm = newEmail.getText().toString();
                String newIDString = newId.getText().toString();
                String jsonString = "";

                if (isInteger(newIDString)) {
                    Toast.makeText(CreateNewUserActivity.this, "Creating New User", Toast.LENGTH_SHORT).show();

                    try {
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
                    makeStringReq(jsonString);
                } else {
                    Toast.makeText(CreateNewUserActivity.this, "Your User ID Should Be An Integer", Toast.LENGTH_SHORT).show();
                }
            }

            /**
             *
             * @param s
             * @return true/false if the param is an integer
             */
            public boolean isInteger(String s){
                try{
                    int d = Integer.parseInt(s);
                } catch (NumberFormatException nfe){
                    return false;
                }
                return true;
            }

            public void makeStringReq(String requestBody) {
                //manually create and add params to get url request string
                String s = URL_STRING_REQ;

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        s,
                        new Response.Listener<String>() {
                            /**
                             *
                             * @param response
                             *
                             * Ons successful response of string req
                             */
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
