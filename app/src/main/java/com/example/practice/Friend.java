package com.example.practice;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class Friend implements Serializable {
    private String username;
    private String email;
    private String firstName;
    private String lastName;

    public Friend() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String fName) {
        this.firstName = fName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lName) {
        this.lastName = lName;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "name='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fName='" + firstName + '\'' +
                ", lName='" + lastName + '\'' +
                '}';
    }
}