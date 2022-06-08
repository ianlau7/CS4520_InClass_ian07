package com.example.practice;

import androidx.annotation.Keep;

@Keep
public class Note {

    private String _id, userId, text;
    private int __v;

    public Note (String _id, String userId, String text, int __v) {
        this._id = _id;
        this.userId = userId;
        this.text = text;
        this.__v = __v;
    }

    public Note() {}

    public int getV() {
        return __v;
    }

    public String getText() {
        return text;
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return _id;
    }

    public void setV(int v) {
        this.__v = v;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(String id) {
        this._id = id;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + _id + '\'' +
                ", userId='" + userId + '\'' +
                ", text='" + text + '\'' +
                ", v=" + __v +
                '}';
    }
}
