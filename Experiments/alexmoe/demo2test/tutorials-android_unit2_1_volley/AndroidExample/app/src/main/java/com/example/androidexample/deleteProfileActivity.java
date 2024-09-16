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

public class deleteProfileActivity extends AppCompatActivity{

    final String URL_STRING_REQ = "http://coms-309-064.class.las.iastate.edu:8080/users/";

    private EditText userIDInput;

    private TextView userText2;
    public CurrentUser currentUser;
    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_profile);

        Intent intent = getIntent();
        //no extra?
        if(intent != null && intent.hasExtra("currentUser")) {
            currentUser = (CurrentUser) intent.getSerializableExtra("currentUser");
        } else {
            System.out.println("no intent or no user");
        }

        Button menuBtn = findViewById(R.id.menuBtn);
        Button deleteUser = findViewById(R.id.deleteBtn);

        userText2 = (TextView) findViewById(R.id.userText2);

        String name = currentUser.getUsername();
        userText2.setText(name);

        deleteUser.setOnClickListener(new View.OnClickListener() {
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
                    makeStringReq(userID);
                } else {
                    Toast.makeText(deleteProfileActivity.this, "Your User ID Should Be An Integer", Toast.LENGTH_SHORT).show();
                }

            }

            //Check if entered String is an integer
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
                        Request.Method.DELETE,
                        s,
                        new Response.Listener<String>() {
                            /**
                             *
                             * @param response
                             *
                             * Deletes the entered user ID
                             */
                            @Override
                            public void onResponse(String response) {
                                // Handle the successful response here
                                Log.d("Volley Response", response);
                                Toast.makeText(deleteProfileActivity.this, "User Deleted", Toast.LENGTH_SHORT).show();
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

        menuBtn.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}