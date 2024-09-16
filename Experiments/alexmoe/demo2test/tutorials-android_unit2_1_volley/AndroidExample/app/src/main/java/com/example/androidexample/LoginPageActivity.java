package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.io.Serializable;

public class LoginPageActivity extends AppCompatActivity {

    private EditText username, password;

    public String  enteredUsername;
    public String enteredPassword;

    //list of users in database
   final String URL_STRING_REQ = "http://coms-309-064.class.las.iastate.edu:8080/users/login";

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        Button loginBtn = findViewById(R.id.loginBtn);
        Button createUserBtn = findViewById(R.id.createUser);

        createUserBtn.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                int id = view.getId();

                if (id == R.id.createUser) {
                    startActivity(new Intent(LoginPageActivity.this, CreateNewUserActivity.class));
                }

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                int id = view.getId();

                username = findViewById(R.id.username);
                password = findViewById(R.id.password);

                //Get the string entered by user into each box
                enteredUsername = username.getText().toString();
                enteredPassword = password.getText().toString();

                /*
                Send to function below to check if the username and password entered match a
                username/password combo in the database
                 */
                makeStringReq(enteredUsername, enteredPassword);

            }

            public void makeStringReq(String givenUser, String givenPass) {
                //manually create and add params to get url request string
                String s = URL_STRING_REQ + "?username="+givenUser+"&password="+givenPass;
                StringRequest stringRequest = new StringRequest(
                        Request.Method.GET,
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
                                if(response.equals("true")) {
                                    Toast.makeText(LoginPageActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                                    CurrentUser currentUser = new CurrentUser(enteredUsername, enteredPassword);
                                    Intent intent = new Intent(LoginPageActivity.this, homePageActivity.class);
                                    intent.putExtra("currentUser", currentUser);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginPageActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
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
                };

                // Adding request to request queue
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }

        });

    }

}