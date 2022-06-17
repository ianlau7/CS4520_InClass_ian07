package com.example.practice;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@Keep
public class in_class_08 extends AppCompatActivity
        implements ChatLoginFragment.IloginFragmentAction,
        FriendsAdapter.IfriendsListRecyclerAction,
        ChatRegisterFragment.IregisterFragmentAction,
        ChatSpecificFragment.IspecificFragmentAction {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Friend currentFriend;
    private boolean inChat;
    private static boolean fromCamera = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class08);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.signOut();
        currentUser = mAuth.getCurrentUser();
        inChat = false;
        populateScreen();
    }

    private void populateScreen() {
        //      Check for Authenticated users ....
        if(currentUser != null){
            if(!inChat) {
                //The user is authenticated, Populating The Main Fragment....
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMain, ChatMainFragment.newInstance(),"mainFragment")
                        .commit();
            } else {
                //The user is authenticated, Populating The Main Fragment....
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMain, ChatSpecificFragment.newInstance(currentFriend),"chatFragment")
                        .commit();
            }

        }else if (!fromCamera) {
            if (!inChat) {
//            The user is not logged in, load the login Fragment....
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMain, ChatLoginFragment.newInstance(), "loginFragment")
                        .commit();
            }
        }
        fromCamera = false;
    }

    @Override
    public void populateMainFragment(FirebaseUser mUser) {
        this.currentUser = mUser;
        populateScreen();
    }
    @Override
    public void registerDone(FirebaseUser mUser) {
        this.currentUser = mUser;
        populateScreen();
    }

    @Override
    public void populateRegisterFragment() {
//            The user needs to create an account, load the register Fragment....
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerMain, new ChatRegisterFragment(),"registerFragment")
                .commit();
    }

    @Override
    public void chatButtonClickedFromRecylcerView(Friend friend) {
        currentFriend = friend;
        inChat = true;
        populateScreen();
    }

    @Override
    public void exitChat() {
        inChat = false;
        populateScreen();
    }

    public static void setFromCamera() {
        fromCamera = true;
    }
}