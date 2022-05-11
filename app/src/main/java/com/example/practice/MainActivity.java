package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonPrac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiate IDs
        buttonPrac = findViewById(R.id.practice);

        // set texts
        buttonPrac.setText("Practice");

        // practice button
        buttonPrac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPractice = new Intent(MainActivity.this,
                        in_class_practice.class);
                startActivity(toPractice);
            }
        });

    }


}