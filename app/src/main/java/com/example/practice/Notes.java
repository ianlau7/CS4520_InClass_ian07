package com.example.practice;

import androidx.annotation.Keep;

import java.util.ArrayList;

@Keep
public class Notes {

    private ArrayList<Note> notes;

    public Notes (ArrayList<Note> notes) {
        this.notes = notes;
    }

    public Notes() {}

    public ArrayList<Note> getNotes() {
        return this.notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "notes=" + notes +
                '}';
    }
}
