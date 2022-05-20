package com.example.practice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link userDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class userDisplayFragment extends Fragment {

    ImageView avatar, emoji;
    TextView nameResult, emailResult, platform, mood;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String name = "name";
    private static final String email = "email";
    private static final String moodString = "mood";
    private static final String platformString = "platform";
    private static final String avatarId = "avatar id";

    // TODO: Rename and change types of parameters
     private String name_param;
     private String email_param;
     private String mood_param;
     private String platform_param;
     private int avatar_id_param;

    public userDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     * @return A new instance of fragment userDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static userDisplayFragment newInstance(Profile user) {
        userDisplayFragment fragment = new userDisplayFragment();
        Bundle args = new Bundle();
        args.putString(name, user.name);
        args.putString(email, user.email);
        args.putString(moodString, user.mood);
        args.putString(platformString, user.platform);
        args.putInt(avatarId, user.avatarId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Display Activity");

        if (getArguments() != null) {
            name_param = getArguments().getString(name);
            email_param = getArguments().getString(email);
            mood_param = getArguments().getString(moodString);
            platform_param = getArguments().getString(platformString);
            avatar_id_param = getArguments().getInt(avatarId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_display, container, false);

        avatar = view.findViewById(R.id.avatarDisplayImageViewF);
        emoji = view.findViewById(R.id.emojiDisplayImageViewF);
        nameResult = view.findViewById(R.id.nameResultDisplayTextViewF);
        emailResult = view.findViewById(R.id.emailResultDisplaytextViewF);
        platform = view.findViewById(R.id.platformDisplayTextViewF);
        mood = view.findViewById(R.id.moodDisplayTextViewF);

        nameResult.setText(name_param);
        emailResult.setText(email_param);
        platform.setText("I use " + platform_param + "!");
        mood.setText("I am " + mood_param + "!");

        if (mood_param.equals("Sad")) {
            emoji.setImageResource(R.drawable.sad);
        } else if (mood_param.equals("Angry")) {
            emoji.setImageResource(R.drawable.angry);
        } else if (mood_param.equals("Awesome")) {
            emoji.setImageResource(R.drawable.awesome);
        }

        avatar.setImageResource(avatar_id_param);

        return view;
    }

    public void updateValues(String name, String email, String mood0, String platform0, int avatarId) {
        this.name_param = name;
        this.email_param = email;
        this.mood_param = mood0;
        this.platform_param = platform0;
        this.avatar_id_param = avatarId;

        nameResult.setText(name);
        emailResult.setText(email);
        platform.setText("I use " + platform0 + "!");
        mood.setText("I am " + mood0 + "!");

        if (mood0.equals("Sad")) {
            emoji.setImageResource(R.drawable.sad);
        } else if (mood0.equals("Angry")) {
            emoji.setImageResource(R.drawable.angry);
        } else if (mood0.equals("Awesome")) {
            emoji.setImageResource(R.drawable.awesome);
        }

        avatar.setImageResource(avatarId);
    }
}