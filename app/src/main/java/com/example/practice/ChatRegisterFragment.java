package com.example.practice;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

@Keep
public class ChatRegisterFragment extends Fragment implements View.OnClickListener {

    private EditText name, email, password, repeatPassword, firstName, lastName;
    private Button register, takePhoto;
    private String userName, userEmail, userPassword, userRepeatedPassword, userFirstName, userLastName;
    private IregisterFragmentAction mListener;
    private FirebaseFirestore db;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Uri photoURI;

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
        db = FirebaseFirestore.getInstance();
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
        firstName = view.findViewById(R.id.editTextFirstName);
        lastName = view.findViewById(R.id.editTextLastName);
        register = view.findViewById(R.id.chatRegisterButton);
        register.setOnClickListener(this);
        takePhoto = view.findViewById(R.id.chatRegisterTakePhotoButton);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Bundle b = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) b.get("data");
                    photoURI = getImageUri(getActivity(), bitmap);
                }
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        activityResultLauncher.launch(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getActivity(), "No camera available on this device.",
                                Toast.LENGTH_LONG).show();
                    }


                }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        this.userName = String.valueOf(name.getText()).trim();
        this.userEmail = String.valueOf(email.getText()).trim();
        this.userPassword = String.valueOf(password.getText()).trim();
        this.userRepeatedPassword = String.valueOf(repeatPassword.getText()).trim();
        this.userFirstName = String.valueOf(firstName.getText()).trim();
        this.userLastName = String.valueOf(lastName.getText()).trim();

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

                                    Map<String, Object> newUser = new HashMap<>();
                                    newUser.put("email", userEmail);
                                    newUser.put("username", userName);
                                    newUser.put("firstName", userFirstName);
                                    newUser.put("lastName", userLastName);
                                    if (photoURI != null) {
                                        newUser.put("profilePicture", photoURI);
                                    }
                                    db.collection("users").document(userName).set(newUser);

//                                    Adding name to the FirebaseUser...
                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(userName)
                                            .setPhotoUri(photoURI)
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes); // Used for compression rate of the Image : 100 means no compression
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "xyz", null);
        return Uri.parse(path);
    }
}