package com.example.practice;

import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link avatarSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Keep
public class avatarSelectFragment extends Fragment {

    ImageView avatar1, avatar2, avatar3, avatar4, avatar5, avatar6;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public avatarSelectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment avatarSelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static avatarSelectFragment newInstance(String param1, String param2) {
        avatarSelectFragment fragment = new avatarSelectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Select Avatar");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_avatar_select, container, false);

        avatar1 = view.findViewById(R.id.avatar1F);
        avatar2 = view.findViewById(R.id.avatar2F);
        avatar3 = view.findViewById(R.id.avatar3F);
        avatar4 = view.findViewById(R.id.avatar4F);
        avatar5 = view.findViewById(R.id.avatar5F);
        avatar6 = view.findViewById(R.id.avatar6F);

        avatar1.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                in_class_03.avatarSelect.setImageResource(R.drawable.avatar_f_1);
                in_class_03.avatarResId = R.drawable.avatar_f_1;
                in_class_03.hasAvatar = true;
                getActivity().getSupportFragmentManager().popBackStack();
                in_class_03.submit.setVisibility(View.VISIBLE);
                getActivity().setTitle("Edit Profile Activity");
            }
        });

        avatar2.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                in_class_03.avatarSelect.setImageResource(R.drawable.avatar_m_3);
                in_class_03.avatarResId = R.drawable.avatar_m_3;
                in_class_03.hasAvatar = true;
                getActivity().getSupportFragmentManager().popBackStack();
                in_class_03.submit.setVisibility(View.VISIBLE);
                getActivity().setTitle("Edit Profile Activity");
            }
        });

        avatar3.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                in_class_03.avatarSelect.setImageResource(R.drawable.avatar_f_2);
                in_class_03.avatarResId = R.drawable.avatar_f_2;
                in_class_03.hasAvatar = true;
                getActivity().getSupportFragmentManager().popBackStack();
                in_class_03.submit.setVisibility(View.VISIBLE);
                getActivity().setTitle("Edit Profile Activity");
            }
        });

        avatar4.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                in_class_03.avatarSelect.setImageResource(R.drawable.avatar_m_2);
                in_class_03.avatarResId = R.drawable.avatar_m_2;
                in_class_03.hasAvatar = true;
                getActivity().getSupportFragmentManager().popBackStack();
                in_class_03.submit.setVisibility(View.VISIBLE);
                getActivity().setTitle("Edit Profile Activity");
            }
        });

        avatar5.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                in_class_03.avatarSelect.setImageResource(R.drawable.avatar_f_3);
                in_class_03.avatarResId = R.drawable.avatar_f_3;
                in_class_03.hasAvatar = true;
                getActivity().getSupportFragmentManager().popBackStack();
                in_class_03.submit.setVisibility(View.VISIBLE);
                getActivity().setTitle("Edit Profile Activity");
            }
        });

        avatar6.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                in_class_03.avatarSelect.setImageResource(R.drawable.avatar_m_1);
                in_class_03.avatarResId = R.drawable.avatar_m_1;
                in_class_03.hasAvatar = true;
                getActivity().getSupportFragmentManager().popBackStack();
                in_class_03.submit.setVisibility(View.VISIBLE);
                getActivity().setTitle("Edit Profile Activity");
            }
        });

        // Inflate the layout for this fragment
        return view;

    }
}