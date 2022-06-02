package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class newsDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);

        if (getIntent() != null && getIntent().getExtras() != null) {
            ArrayList<Article> articles = getIntent().getParcelableArrayListExtra("articles");

            // display and change frontend here
            
        }
    }
}