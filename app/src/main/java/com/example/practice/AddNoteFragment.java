package com.example.practice;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
 * Use the {@link AddNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Keep
public class AddNoteFragment extends Fragment {

    OkHttpClient client = new OkHttpClient();

    private EditText text;
    private Button add;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "token";

    // TODO: Rename and change types of parameters
    private String token;
    IAddButtonActions listener;

    public AddNoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AddNoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNoteFragment newInstance(String param1) {
        AddNoteFragment fragment = new AddNoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            token = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        text = view.findViewById(R.id.typeNoteEditText);
        add = view.findViewById(R.id.addNoteFragmentButton);

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (text.getText().length() == 0) {
                    Toast.makeText(getActivity(), "Please enter a note",
                            Toast.LENGTH_LONG).show();
                } else {
                    RequestBody formBody = new FormBody.Builder()
                            .add("text", text.getText().toString())
                            .build();

                    Request request = new Request.Builder()
                            .url("http://dev.sakibnm.space:3000/api/note/post")
                            .addHeader("x-access-token", token)
                            .post(formBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Error with JSON (1)",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if (response.isSuccessful()) {

                                String string = response.body().string();

                                try {

                                    JSONObject json = new JSONObject(string);
                                    String userId = json.getJSONObject("note").getString("userId");
                                    String _id = json.getJSONObject("note").getString("_id");
                                    int __v = json.getJSONObject("note").getInt("__v");
                                    String textJSON = json.getJSONObject("note").getString("text");

                                    Note note = new Note(_id, userId, textJSON, __v);

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            listener.addButtonClicked(note);
                                            text.getText().clear();
                                            getActivity().getSupportFragmentManager().popBackStack();
                                        }
                                    });

                                } catch (JSONException e) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "Error with JSON (2)",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "Error with JSON (3)",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IAddButtonActions) {
            listener = (IAddButtonActions) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement");
        }
    }

    public interface IAddButtonActions {
        void addButtonClicked(Note note);
    }
}