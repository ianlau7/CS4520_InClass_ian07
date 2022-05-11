package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class in_class_practice extends AppCompatActivity {

    final String TAG = "demo";

    Button button;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class_practice);
        setTitle("Practice In Class");

        // instantiate IDs
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        // set texts
        button.setText("Log Cat");
        button2.setText("Toast");

        // log cat button listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Practice!Practice!!Practice!!!");
            }
        });

        // toast button listener
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(in_class_practice.this, "Now push to GitHub and Submit!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}