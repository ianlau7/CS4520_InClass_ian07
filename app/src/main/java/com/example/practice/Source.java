package com.example.practice;

import androidx.annotation.Keep;

@Keep
public class Source {

    private String id;
    private String name;

    public Source (String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Source() {}

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Source{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
