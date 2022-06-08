package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class in_class_07 extends AppCompatActivity implements AddNoteFragment.IAddButtonActions {

    static Button login;
    static Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class07);
        setTitle("Please Login/Register");

        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerButton);

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login.setVisibility(View.INVISIBLE);
                register.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.registerFragmentContainer, new RegisterFragment())
                        .addToBackStack("register")
                        .commit();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login.setVisibility(View.INVISIBLE);
                register.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.registerFragmentContainer, new LoginFragment())
                        .addToBackStack("login")
                        .commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        int stacks = getSupportFragmentManager().getBackStackEntryCount();

        if (stacks == 1) {
            setTitle("Login/Register");
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);
            getSupportFragmentManager().popBackStack();
        } else if(stacks == 2) {
            setTitle("Login");
            getSupportFragmentManager().popBackStack();
        }else if (stacks > 2) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }

    }

    @Override
    public void addButtonClicked(Note note) {
        NotesFragment.addNote(note);
    }
}