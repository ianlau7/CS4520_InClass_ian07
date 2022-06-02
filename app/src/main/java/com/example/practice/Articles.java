package com.example.practice;

import java.util.ArrayList;

public class Articles {
    String status;
    int totalResults;
    protected ArrayList<Article> articles;

    public Articles (String status, int totalResults, ArrayList<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public ArrayList<Article> getArticles() {
        return this.articles;
    }
}
