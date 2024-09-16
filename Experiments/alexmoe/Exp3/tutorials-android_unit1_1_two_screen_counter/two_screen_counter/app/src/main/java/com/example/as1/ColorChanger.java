package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
public class ColorChanger extends AppCompatActivity{

    Button colorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Changing layout to color_picker option
        setContentView(R.layout.color_picker);

        colorPicker.findViewById(R.id.colorButton);

        final RelativeLayout relativeLayout = findViewById(R.id.rlVar1);

        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                relativeLayout.setBackgroundResource(R.color.cool);
            }
        });
    }
}
