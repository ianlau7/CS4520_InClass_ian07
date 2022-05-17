package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class avatarSelect extends AppCompatActivity {

    ImageView avatar1, avatar2, avatar3, avatar4, avatar5, avatar6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_select);
        setTitle("Select Avatar");

        avatar1 = findViewById(R.id.avatar1);
        avatar2 = findViewById(R.id.avatar2);
        avatar3 = findViewById(R.id.avatar3);
        avatar4 = findViewById(R.id.avatar4);
        avatar5 = findViewById(R.id.avatar5);
        avatar6 = findViewById(R.id.avatar6);

        avatar1.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                in_class_02.avatarSelect.setImageResource(R.drawable.avatar_f_1);
                in_class_02.avatarResId = R.drawable.avatar_f_1;
                in_class_02.hasAvatar = true;
                finish();
            }
        });

        avatar2.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                in_class_02.avatarSelect.setImageResource(R.drawable.avatar_m_3);
                in_class_02.avatarResId = R.drawable.avatar_m_3;
                in_class_02.hasAvatar = true;
                finish();
            }
        });

        avatar3.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                in_class_02.avatarSelect.setImageResource(R.drawable.avatar_f_2);
                in_class_02.avatarResId = R.drawable.avatar_f_2;
                in_class_02.hasAvatar = true;
                finish();
            }
        });

        avatar4.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                in_class_02.avatarSelect.setImageResource(R.drawable.avatar_m_2);
                in_class_02.avatarResId = R.drawable.avatar_m_2;
                in_class_02.hasAvatar = true;
                finish();
            }
        });

        avatar5.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                in_class_02.avatarSelect.setImageResource(R.drawable.avatar_f_3);
                in_class_02.avatarResId = R.drawable.avatar_f_3;
                in_class_02.hasAvatar = true;
                finish();
            }
        });

        avatar6.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                in_class_02.avatarSelect.setImageResource(R.drawable.avatar_m_1);
                in_class_02.avatarResId = R.drawable.avatar_m_1;
                in_class_02.hasAvatar = true;
                finish();
            }
        });

    }
}