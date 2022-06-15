package com.example.practice;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatEditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatEditProfileFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    ImageView profilePicture;
    EditText name;
    Button finish;

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

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat_edit_profile, container, false);

        profilePicture = view.findViewById(R.id.chatEditProfileImageView);
        name = view.findViewById(R.id.chatEditProfileEditNameEditText);
        finish = view.findViewById(R.id.chatEditProfileFinishButton);

        name.setHint(mUser.getDisplayName());

        profilePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // camera stuff
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String displayName = mUser.getDisplayName();
                Uri imageURL = mUser.getPhotoUrl();

                if (name.getText().length() > 0) {
                    displayName = name.getText().toString();
                }

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName)

                        // change this to camera photo
                        .setPhotoUri(imageURL)

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
}