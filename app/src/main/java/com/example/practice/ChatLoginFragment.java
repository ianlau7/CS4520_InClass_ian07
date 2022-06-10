package com.example.practice;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatLoginFragment extends Fragment implements View.OnClickListener {

    private EditText email, password;
    private Button login, register;
    private String userEmail;
    private String userPassword;
    private FirebaseAuth mAuth;
    private IloginFragmentAction mListener;

    public ChatLoginFragment() {
        // Required empty public constructor
    }

    public static ChatLoginFragment newInstance() {
        ChatLoginFragment fragment = new ChatLoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Login");

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat_login, container, false);

        email = view.findViewById(R.id.chatLoginEditTextTextEmailAddress);
        password = view.findViewById(R.id.chatLoginEditTextTextPassword);
        login = view.findViewById(R.id.chatLoginButton);
        register = view.findViewById(R.id.chatGoToRegisterButton);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IloginFragmentAction){
            this.mListener = (IloginFragmentAction) context;
        }else{
            throw new RuntimeException(context.toString()+ "must implement PopulateMainFragment");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.chatLoginButton) {
            userEmail = email.getText().toString().trim();
            userPassword = password.getText().toString().trim();
            if (email.getText().length() == 0) {
                Toast.makeText(getContext(), "Please enter an email.", Toast.LENGTH_LONG).show();
            }
            if (password.getText().length() == 0) {
                Toast.makeText(getContext(), "Please enter a password.", Toast.LENGTH_LONG).show();
            }
            if (!userEmail.equals("") && !userPassword.equals("")) {
//                    Sign in to the account....
                mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getContext(), "Login Successful!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Login Failed!" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mListener.populateMainFragment(mAuth.getCurrentUser());
                                }
                            }
                        });
            }
        }
    }

    public interface IloginFragmentAction {
        void populateMainFragment(FirebaseUser mUser);
        void populateRegisterFragment();
    }
}