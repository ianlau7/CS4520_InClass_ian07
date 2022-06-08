package com.example.practice;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    EditText name, email, password;
    Button register;
    private String token;
    OkHttpClient client = new OkHttpClient();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Register");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        name = view.findViewById(R.id.registerNameEditText);
        email = view.findViewById(R.id.registerEmailEditText);
        password = view.findViewById(R.id.registerPasswordEditText);
        register = view.findViewById(R.id.registerFragmentButton);

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // if no name input
                if (name.getText().length() == 0) {
                    Toast.makeText(getActivity(), "Please enter your name",
                            Toast.LENGTH_LONG).show();
                }

                // if email EditText is empty/wrong
                else if (email.getText().length() == 0 ||
                        !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    Toast.makeText(getActivity(), "Please enter a valid email",
                            Toast.LENGTH_LONG).show();

                // if password length is less than 8
                } else if (password.getText().length() < 8) {
                    Toast.makeText(getActivity(), "Please enter a password with at least 8 " +
                                    "characters.",
                            Toast.LENGTH_LONG).show();

                } else {

                    RequestBody formBody = new FormBody.Builder()
                            .add("name", name.getText().toString())
                            .add("email", email.getText().toString())
                            .add("password", password.getText().toString())
                            .build();

                    Request request = new Request.Builder()
                            .url("http://dev.sakibnm.space:3000/api/auth/register")
                            .post(formBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                            if (response.isSuccessful()) {
                                String string = response.body().string();

                                try {
                                    JSONObject json = new JSONObject(string);

                                    token = json.getString("token");

                                    if (json.getBoolean("auth")) {

                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(), "You have " +
                                                                "successfully registered!" +
                                                                " Please login.",
                                                        Toast.LENGTH_LONG).show();

                                                getActivity().getSupportFragmentManager().popBackStack();
                                                in_class_07.login.setVisibility(View.VISIBLE);
                                                in_class_07.register.setVisibility(View.VISIBLE);
                                                getActivity().setTitle("Please Login/Register");
                                            }
                                        });
                                    }

                                } catch (JSONException e) {

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "Something went wrong." +
                                                            " Please try again.",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "A user with this email " +
                                                        "and/or name already exists.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                        }

                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "A user with this email already exists.",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                }

            }
        });


        return view;
    }
}