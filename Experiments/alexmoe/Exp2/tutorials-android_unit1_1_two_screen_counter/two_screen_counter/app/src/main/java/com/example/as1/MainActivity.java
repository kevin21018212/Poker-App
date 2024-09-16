package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button button;
    private ImageView imageView;
    private AnimatedVectorDrawable emptyHeart;
    private AnimatedVectorDrawable fillHeart;
    private boolean full = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.toCounterBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CounterActivity.class);
                startActivity(intent);
            }
        });
//ToDo
      //  imageView = findViewById(R.id.image_view);
        emptyHeart
                = (AnimatedVectorDrawable)
                getDrawable(
                        R.drawable.avd_heart_empty);
        fillHeart
                = (AnimatedVectorDrawable)
                getDrawable(
                        R.drawable.avd_heart_fill);

    }

    // This method help to animate our view.
    public void animate(View view)
    {
        AnimatedVectorDrawable drawable
                = full
                ? emptyHeart
                : fillHeart;
        imageView.setImageDrawable(drawable);
        drawable.start();
        full = !full;
    }
}