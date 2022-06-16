package com.example.practice;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "email";
    private static final String ARG_PARAM3 = "password";
    private static final String ARG_PARAM4 = "repeatPassword";
    private static final String ARG_PARAM5 = "firstName";
    private static final String ARG_PARAM6 = "lastName";
    private static final String ARG_PARAM7 = "photoURI";

    private Button register, takePhoto;
    private String userName, userEmail, userPassword, userRepeatedPassword, userFirstName, userLastName;
    private IregisterFragmentAction mListener;
    private FirebaseFirestore db;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private String photoURIString;
    Uri photoURI;

    public ChatRegisterFragment() {
        // Required empty public constructor
    }

    public static ChatRegisterFragment newInstance(String name, String email, String password,
                                                   String repeatedPassword, String firstName,
                                                   String lastName, String photoURI) {
        ChatRegisterFragment fragment = new ChatRegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        args.putString(ARG_PARAM2, email);
        args.putString(ARG_PARAM3, password);
        args.putString(ARG_PARAM4, repeatedPassword);
        args.putString(ARG_PARAM5, firstName);
        args.putString(ARG_PARAM6, lastName);
        args.putString(ARG_PARAM7, photoURI);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Register");
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        if (getArguments() != null) {
            userName = getArguments().getString(ARG_PARAM1);
            userEmail = getArguments().getString(ARG_PARAM2);
            userPassword = getArguments().getString(ARG_PARAM3);
            userRepeatedPassword = getArguments().getString(ARG_PARAM4);
            userFirstName = getArguments().getString(ARG_PARAM5);
            userLastName = getArguments().getString(ARG_PARAM6);
            photoURI = Uri.parse(getArguments().getString(ARG_PARAM7));
        }
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

        if (userName != null) {
            name.setText(userName);
        }

        if (userEmail != null) {
            email.setText(userEmail);
        }

        if (userPassword != null) {
            password.setText(userPassword);
        }

        if (userRepeatedPassword != null) {
            repeatPassword.setText(userRepeatedPassword);
        }

        if (userFirstName != null) {
            firstName.setText(userFirstName);
        }

        if (userLastName != null) {
            lastName.setText(userLastName);
        }

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

                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.containerMain, ChatRegisterFragment.newInstance(
                                            name.getText().toString(),
                                            email.getText().toString(),
                                            password.getText().toString(),
                                            repeatPassword.getText().toString(),
                                            firstName.getText().toString(),
                                            lastName.getText().toString(),
                                            photoURI.toString()),"registerFragment")
                                    .commit();

                        }
                    }
                });

        takePhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                requestRead();
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
                                    db.collection("users").document(userEmail).set(newUser);

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

    public void requestRead() {
        if (ContextCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            readFile();
        }
    }

    public void readFile() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            activityResultLauncher.launch(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "No camera available on this device.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readFile();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes); // Used for compression rate of the Image : 100 means no compression
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "xyz", null);
        Uri temp = Uri.parse(path);
        return temp;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable("photoURI", photoURI);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

        if (savedInstanceState !=null && savedInstanceState.containsKey("photoURI")) {
            photoURI = savedInstanceState.getParcelable("photoURI");
        }

        super.onViewStateRestored(savedInstanceState);
    }
}