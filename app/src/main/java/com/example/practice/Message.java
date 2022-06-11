package com.example.practice;

import java.io.Serializable;

public class Message implements Serializable {
    private String message;
    private String sender;

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
