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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatRegisterFragment extends Fragment implements View.OnClickListener {

    private EditText name, email, password, repeatPassword;
    private Button register;
    private String userName, userEmail, userPassword, userRepeatedPassword;
    private IregisterFragmentAction mListener;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public ChatRegisterFragment() {
        // Required empty public constructor
    }

    public static ChatRegisterFragment newInstance() {
        ChatRegisterFragment fragment = new ChatRegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Register");

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IregisterFragmentAction){
            this.mListener = (IregisterFragmentAction) context;
        }else{
            throw new RuntimeException(context.toString()
                    + "must implement RegisterRequest");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_register, container, false);

        name = view.findViewById(R.id.chatRegisterEditTextTextPersonName);
        email = view.findViewById(R.id.chatRegisterEditTextTextEmailAddress);
        password = view.findViewById(R.id.chatRegisterEditTextTextPassword);
        repeatPassword = view.findViewById(R.id.chatRegisterReEnterEditTextTextPassword);
        register = view.findViewById(R.id.chatRegisterButton);
        register.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        this.userName = String.valueOf(name.getText()).trim();
        this.userEmail = String.valueOf(email.getText()).trim();
        this.userPassword = String.valueOf(password.getText()).trim();
        this.userRepeatedPassword = String.valueOf(repeatPassword.getText()).trim();

        if(v.getId()== R.id.chatRegisterButton){
            if(name.getText().length() == 0){
                Toast.makeText(getContext(), "Please enter a name.", Toast.LENGTH_LONG).show();
            }
            if(email.getText().length() == 0){
                Toast.makeText(getContext(), "Please enter an email.", Toast.LENGTH_LONG).show();
            }
            if(password.getText().length() == 0){
                Toast.makeText(getContext(), "Please enter a password.", Toast.LENGTH_LONG).show();
            }
            if(!userRepeatedPassword.equals(userPassword)){
                Toast.makeText(getContext(), "Make sure both password fields are the same.", Toast.LENGTH_LONG).show();
            }

            if(!userName.equals("") && !userEmail.equals("")
                    && !userPassword.equals("")
                    && userRepeatedPassword.equals(userPassword)){
                mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mUser = task.getResult().getUser();

//                                    Adding name to the FirebaseUser...
                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(userName)
                                            .build();

                                    mUser.updateProfile(profileChangeRequest)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        mListener.registerDone(mUser);
                                                    }
                                                }
                                            });

                                }
                            }
                        });
            }
        }
    }

    public interface IregisterFragmentAction {
        void registerDone(FirebaseUser mUser);
    }
}