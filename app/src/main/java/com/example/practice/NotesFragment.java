package com.example.practice;

import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Keep
public class NotesFragment extends Fragment implements AddNoteFragment.IAddButtonActions {

    OkHttpClient client = new OkHttpClient();

    private Button logout, profile, add;
    private RecyclerView recyclerView;
    private static NotesAdapter notesAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private static ArrayList<Note> notes;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "token";

    // TODO: Rename and change types of parameters
    private String token;

    public NotesFragment() {
        // Required empty public constructor
    }

    public static void addNote(Note note) {
        notes.add(note);
        notesAdapter.notifyDataSetChanged();

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment NotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(String param1) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Notes");

        if (getArguments() != null) {
            token = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        profile = view.findViewById(R.id.notesProfileButton);
        logout = view.findViewById(R.id.notesLogoutButton);
        add = view.findViewById(R.id.notesAddButton);

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                token = "";
                LoginFragment.setToken("");
                notesAdapter.setToken("");
                getActivity().setTitle("Login");
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               AddNoteFragment addNoteFragment = new AddNoteFragment();
                Bundle args = new Bundle();
                args.putString("token", token);
                addNoteFragment.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.addNoteFragmentContainer,
                                addNoteFragment)
                        .addToBackStack("add note")
                        .commit();

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Request request = new Request.Builder()
                        .url("http://dev.sakibnm.space:3000/api/auth/me")
                        .addHeader("x-access-token", token)
                        .build();

                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        if(response.isSuccessful()) {
                            String string = response.body().string();

                            try {
                                JSONObject json = new JSONObject(string);

                                String name = json.getString("name");
                                String email = json.getString("email");

                                NotesProfileDisplayFragment userDisplay = new NotesProfileDisplayFragment();
                                Bundle args = new Bundle();
                                args.putString("name", name);
                                args.putString("email", email);
                                userDisplay.setArguments(args);

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getActivity().setTitle("My Profile");
                                    }
                                });

                                // go to user display here
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.registerFragmentContainer,
                                                userDisplay)
                                        .addToBackStack("me")
                                        .commit();

                            } catch (JSONException e) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "Error with JSON",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "No user data",
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
                                Toast.makeText(getActivity(), "No user data",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });

        Request request = new Request.Builder()
                .url("http://dev.sakibnm.space:3000/api/note/getall")
                .addHeader("x-access-token", token)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    Gson gsonData = new Gson();
                    Notes notesObj = gsonData.fromJson(Objects.requireNonNull(response.body()).
                            charStream(), Notes.class);
                    notes = notesObj.getNotes();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView = view.findViewById(R.id.notesRecyclerView);
                            recyclerViewLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(recyclerViewLayoutManager);
                            notesAdapter = new NotesAdapter(notes, token);
                            recyclerView.setAdapter(notesAdapter);
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "No notes",
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
                        Toast.makeText(getActivity(), "no notes",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void addButtonClicked(Note note) {
        notes.add(note);
        notesAdapter.notifyDataSetChanged();
    }
}