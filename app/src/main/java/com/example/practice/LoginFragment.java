package com.example.practice;

import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Keep
public class LoginFragment extends Fragment {

    OkHttpClient client = new OkHttpClient();

    EditText email, password;
    Button login;
    private static String token;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Login");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        email = view.findViewById(R.id.loginEmailEditText);
        password = view.findViewById(R.id.loginPasswordEditText);
        login = view.findViewById(R.id.loginFragmentButton);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // if email EditText is empty/wrong
                if (email.getText().length() == 0 ||
                        !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    Toast.makeText(getActivity(), "Please enter a valid email",
                            Toast.LENGTH_LONG).show();


                } else {

                    RequestBody formBody = new FormBody.Builder()
                            .add("email", email.getText().toString())
                            .add("password", password.getText().toString())
                            .build();

                    Request request = new Request.Builder()
                            .url("http://dev.sakibnm.space:3000/api/auth/login")
                            .post(formBody)
                            .build();

                    email.getText().clear();
                    password.getText().clear();

                    client.newCall(request).enqueue(new Callback() {

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                            if (response.isSuccessful()) {
                                String string = response.body().string();

                                try {
                                    JSONObject json = new JSONObject(string);

                                    if (json.getBoolean("auth")) {

                                        token = json.getString("token");

                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(), "You have " +
                                                                "successfully logged in!",
                                                        Toast.LENGTH_LONG).show();

                                                getActivity().setTitle("Notes");

                                                NotesFragment notesFragment = new NotesFragment();
                                                Bundle args = new Bundle();
                                                args.putString("token", token);
                                                notesFragment.setArguments(args);

                                                // go to notes here
                                                getActivity().getSupportFragmentManager().beginTransaction()
                                                        .replace(R.id.registerFragmentContainer,
                                                                notesFragment)
                                                        .addToBackStack("notes")
                                                        .commit();

                                            }
                                        });
                                    } else {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(), "Incorrect password.",
                                                        Toast.LENGTH_LONG).show();
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
                                                        "does not exist",
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
                                    Toast.makeText(getActivity(), "No user found.",
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

    public static void setToken(String t) {
        token = t;
    }
}
