package com.example.practice;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {

    Source source;
    String url, content;
    protected String title, author, publishedAt, description, urlToImage;

    public Article (Source source, String author, String title, String description,  String url,
                   String urlToImage, String publishedAt, String content) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;


    }

    protected Article(Parcel in) {
        title = in.readString();
        author = in.readString();
        publishedAt = in.readString();
        description = in.readString();
        urlToImage = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getPublishedAt() {
        return this.publishedAt;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUrlToImage() {
        return this.urlToImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(publishedAt);
        dest.writeString(description);
        dest.writeString(urlToImage);
    }
}
