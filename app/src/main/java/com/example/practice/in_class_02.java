package com.example.practice;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

@Keep
public class in_class_02 extends AppCompatActivity {

    EditText name, email;
    static ImageView avatarSelect;
    static int avatarResId;
    TextView iUse, currentMood;
    RadioGroup radioGroup;
    RadioButton android, ios;
    SeekBar moodSeekBar;
    ImageView emoji;
    Button submit;
    String mood;
    static boolean hasAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class_02);
        setTitle("Edit Profile Activity");

        // initialize ids
        name = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editTextTextEmail);
        avatarSelect = findViewById(R.id.selectAvatarImageView);
        iUse = findViewById(R.id.iUseTextView);
        currentMood = findViewById(R.id.currentMoodTextView);
        radioGroup = findViewById(R.id.radioGroup);
        android = findViewById(R.id.androidRadioButton);
        ios = findViewById(R.id.iOSRadioButton);
        moodSeekBar = findViewById(R.id.seekBar);
        emoji = findViewById(R.id.imageView2);
        submit = findViewById(R.id.profileSubmitButton);
        hasAvatar = false;
        mood = "Happy";

        // seek bar changes

        moodSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && progress == 0) {

                    emoji.setImageResource(R.drawable.angry);
                    currentMood.setText("Your current mood: Angry");
                    mood = "Angry";

                } else if (fromUser && progress == 1) {

                    emoji.setImageResource(R.drawable.sad);
                    currentMood.setText("Your current mood: Sad");
                    mood = "Sad";

                } else if (fromUser && progress == 2) {

                    emoji.setImageResource(R.drawable.happy);
                    currentMood.setText("Your current mood: Happy");
                    mood = "Happy";

                } else if (fromUser && progress == 3) {

                    emoji.setImageResource(R.drawable.awesome);
                    currentMood.setText("Your current mood: Awesome");
                    mood = "Awesome";

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // avatar select click
        avatarSelect.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toAvatarSelect = new Intent(in_class_02.this,
                        avatarSelect.class);
                startActivity(toAvatarSelect);
            }
        });

        // submit button click

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // if name EditText is empty
                if (name.getText().length() == 0) {
                    Toast.makeText(in_class_02.this, "Please enter your name",
                            Toast.LENGTH_LONG).show();
                }

                // if email EditText is empty/wrong
                else if (email.getText().length() == 0 ||
                        !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    Toast.makeText(in_class_02.this, "Please enter a valid email",
                            Toast.LENGTH_LONG).show();
                }

                // if radio buttons are unchecked
                else if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(in_class_02.this, "Please select a platform " +
                                    "(Android or iOS)", Toast.LENGTH_LONG).show();
                }

                // if an avatar is not selected
                else if (hasAvatar == false) {
                    Toast.makeText(in_class_02.this, "Please select an avatar",
                            Toast.LENGTH_LONG).show();
                }

                else {
                    try {

                        Intent intent = new Intent(in_class_02.this, userDisplay.class);

                        String n = name.getText().toString();
                        String e = email.getText().toString();

                        int checkedButtonId = radioGroup.getCheckedRadioButtonId();
                        RadioButton checked = findViewById(checkedButtonId);
                        String p = checked.getText().toString();

                        String m = mood;

                        Profile profile = new Profile(n, e, p, m, avatarResId);
                        intent.putExtra("user", profile);

                        startActivity(intent);

                    } catch (IllegalArgumentException e) {
                        Toast.makeText(in_class_02.this, "Something went wrong, " +
                                        "please re-enter your inputs and try again.",
                                Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

    }
}