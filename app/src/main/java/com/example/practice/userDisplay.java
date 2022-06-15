package com.example.practice;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

@Keep
public class userDisplay extends AppCompatActivity {

    ImageView avatar, emoji;
    TextView nameResult, emailResult, platform, mood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_display);
        setTitle("Display Activity");

        avatar = findViewById(R.id.avatarDisplayImageView);
        emoji = findViewById(R.id.emojiDisplayImageView);
        nameResult = findViewById(R.id.nameResultDisplayTextView);
        emailResult = findViewById(R.id.emailResultDisplaytextView);
        platform = findViewById(R.id.platformDisplayTextView);
        mood = findViewById(R.id.moodDisplayTextView);

        if (getIntent() != null && getIntent().getExtras() != null) {
            Profile user = getIntent().getParcelableExtra("user");

            nameResult.setText(user.name);
            emailResult.setText(user.email);
            platform.setText("I use " + user.platform + "!");
            mood.setText("I am " + user.mood + "!");

            if (user.mood.equals("Sad")) {
                emoji.setImageResource(R.drawable.sad);
            } else if (user.mood.equals("Angry")) {
                emoji.setImageResource(R.drawable.angry);
            } else if (user.mood.equals("Awesome")) {
                emoji.setImageResource(R.drawable.awesome);
            }

            avatar.setImageResource(user.avatarId);
        }


    }
}