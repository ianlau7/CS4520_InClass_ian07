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
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatEditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatEditProfileFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private FirebaseFirestore db;
    ImageView profilePicture;
    EditText name, fName, lName;
    Button finish;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> activityResultLauncher;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "auth";
    private static final String ARG_PARAM2 = "user";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatEditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatEditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatEditProfileFragment newInstance(String param1, String param2) {
        ChatEditProfileFragment fragment = new ChatEditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Edit Profile");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat_edit_profile, container, false);

        profilePicture = view.findViewById(R.id.chatEditProfileImageView);
        if (mUser.getPhotoUrl() != null) {
            profilePicture.setImageURI(mUser.getPhotoUrl());
        }
        name = view.findViewById(R.id.chatEditProfileEditUsernameEditText);
        finish = view.findViewById(R.id.chatEditProfileFinishButton);
        fName = view.findViewById(R.id.chatEditProfileEditFirstNameEditText);
        lName = view.findViewById(R.id.chatEditProfileEditLastNameEditText);
        db.collection("users")
                .document(mUser.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                fName.setHint((String) document.getData().get("firstName"));
                                lName.setHint((String) document.getData().get("lastName"));
                            }
                        } else {
                            Log.d("demo", "get failed with ", task.getException());
                        }
                    }
                });
        name.setHint(mUser.getDisplayName());

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            Bundle b = result.getData().getExtras();
                            Bitmap bitmap = (Bitmap) b.get("data");

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()

                                    // change this to camera photo
                                    .setPhotoUri(getImageUri(getActivity(), bitmap))

                                    .build();

                            profilePicture.setImageURI(getImageUri(getActivity(), bitmap));

                            mUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Profile picture updated.",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                        }
                    }
                });

        profilePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                in_class_08.setFromCamera();
                requestRead();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String displayName = mUser.getDisplayName();

                if (name.getText().length() > 0) {
                    displayName = name.getText().toString();
                    db.collection("users")
                            .document(mUser.getEmail())
                            .update("username", displayName);
                }

                if (fName.getText().length() > 0) {
                    db.collection("users")
                            .document(mUser.getEmail())
                            .update("firstName", fName.getText().toString());
                }

                if (lName.getText().length() > 0) {
                    db.collection("users")
                            .document(mUser.getEmail())
                            .update("lastName", lName.getText().toString());
                }

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName)

                        .build();

                mUser.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    getActivity().getSupportFragmentManager().popBackStack();
                                    getActivity().setTitle("Friends");

                                    Toast.makeText(getActivity(), "Profile updated.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        return view;
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes); // Used for compression rate of the Image : 100 means no compression
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "xyz", null);
        Uri temp = Uri.parse(path);
        return temp;
    }
}