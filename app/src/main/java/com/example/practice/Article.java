package com.example.practice;

public class Article {

    protected String title, author, dateAndTimePublished, description, imageURL;

    public Article(String title, String author, String dateAndTimePublished, String desc, String image) {
        this.title = title;
        this.author = author;
        this.dateAndTimePublished = dateAndTimePublished;
        this.description = desc;
        this.imageURL = image;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getDateAndTimePublished() {
        return this.dateAndTimePublished;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImageURL() {
        return this.imageURL;
    }
}
