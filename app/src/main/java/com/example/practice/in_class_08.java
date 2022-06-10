package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class in_class_08 extends AppCompatActivity
        implements ChatLoginFragment.IloginFragmentAction,
        ChatMainFragment.ImainFragmentButtonAction,
        FriendsAdapter.IfriendsListRecyclerAction,
        ChatRegisterFragment.IregisterFragmentAction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class08);
    }
}