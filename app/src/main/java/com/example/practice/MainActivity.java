// Ian Lau
// Assignment 01

package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button inClass01;
    Button inClass02;
    Button inClass03;
    Button inClass04;
    Button inClass05;
    Button inClass06;
    Button inClass07;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Activity");

        // instantiate IDs
        inClass01 = findViewById(R.id.inClass01Button);
        inClass02 = findViewById(R.id.inClass02Button);
        inClass03 = findViewById(R.id.inClass03Button);
        inClass04 = findViewById(R.id.inClass04Button);
        inClass05 = findViewById(R.id.inClass05Button);
        inClass06 = findViewById(R.id.inClass06Button);
        inClass07 = findViewById(R.id.inClass07Button);

        // set texts
        inClass01.setText("In Class 01");
        inClass02.setText("In Class 02");
        inClass03.setText("In Class 03");
        inClass04.setText("In Class 04");
        inClass05.setText("In Class 05");
        inClass06.setText("In Class 06");
        inClass07.setText("In Class 07");

// button to go to in class assignment 1
        inClass01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toInClass01 = new Intent(MainActivity.this,
                        in_class_01.class);
                startActivity(toInClass01);
            }
        });

        // button to go to in class assignment 2
        inClass02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toInClass02 = new Intent(MainActivity.this,
                        in_class_02.class);
                startActivity(toInClass02);
            }
        });

        // button to go to in class assignment 3
        inClass03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toInClass03 = new Intent(MainActivity.this,
                        in_class_03.class);
                startActivity(toInClass03);
            }
        });

        // button to go to in class assignment 4
        inClass04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toInClass04 = new Intent(MainActivity.this,
                        in_class_04.class);
                startActivity(toInClass04);
            }
        });

        // button to go to in class assignment 5
        inClass05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toInClass05 = new Intent(MainActivity.this,
                        in_class_05.class);
                startActivity(toInClass05);
            }
        });

        // button to go to in class assignment 6
        inClass06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toInClass06 = new Intent(MainActivity.this,
                        in_class_06.class);
                startActivity(toInClass06);
            }
        });

        // button to go to in class assignment 7
        inClass07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toInClass07 = new Intent(MainActivity.this,
                        in_class_07.class);
                startActivity(toInClass07);
            }
        });

    }


}