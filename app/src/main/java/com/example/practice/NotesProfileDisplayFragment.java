package com.example.practice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesProfileDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesProfileDisplayFragment extends Fragment {

    private TextView nameTextView, emailTextView;
    private Button back;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "email";

    // TODO: Rename and change types of parameters
    private String name;
    private String email;

    public NotesProfileDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotesProfileDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesProfileDisplayFragment newInstance(String param1, String param2) {
        NotesProfileDisplayFragment fragment = new NotesProfileDisplayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            email = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes_profile_display, container, false);

        nameTextView = view.findViewById(R.id.notesProfileDisplayNameDisplayTextView);
        emailTextView = view.findViewById(R.id.notesProfileDisplayEmailDisplayTextView);
        back = view.findViewById(R.id.notesProfileBackButton);

        nameTextView.setText(name);
        emailTextView.setText(email);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().setTitle("Notes");
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}