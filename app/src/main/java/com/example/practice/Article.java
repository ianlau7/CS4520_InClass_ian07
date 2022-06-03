package com.example.practice;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {

    protected Source source;
    protected String  author;
    protected String  title;
    protected String  description;
    protected String  url;
    protected String  urlToImage;
    protected String  publishedAt;
    protected String  content;

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

    public Article() {}

    public void setSource(Source source) {
        this.source = source;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setContent(String content) {
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
