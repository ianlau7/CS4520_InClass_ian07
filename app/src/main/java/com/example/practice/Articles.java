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

    public Articles() {}

    public ArrayList<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return "Articles: " + articles;
    }
}
