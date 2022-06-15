package com.example.practice;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

@Keep
public class newsDisplay extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private ArrayList<Article> articles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);
        setTitle("Top Headlines");

        if (getIntent() != null && getIntent().getExtras() != null) {
            articles = getIntent().getParcelableArrayListExtra("articles");

            recyclerView = findViewById(R.id.recyclerView);
            recyclerViewLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(recyclerViewLayoutManager);
            newsAdapter = new NewsAdapter(articles);
            recyclerView.setAdapter(newsAdapter);
        }
    }
}