package com.example.practice;

import androidx.annotation.Keep;

import java.util.ArrayList;

@Keep
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

    public int getTotalResults() {
        return totalResults;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Articles{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", articles=" + articles +
                '}';
    }
}
