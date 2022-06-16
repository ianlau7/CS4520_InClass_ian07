package com.example.practice;

import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Keep
public class ChatMainFragment extends Fragment {

    private static final String ARG_FRIENDS = "friendsarray";

    private RecyclerView recyclerView;
    private FriendsAdapter friendsAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private ArrayList<Friend> mFriends;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String email;
    private Button editProfile;

    public ChatMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChatMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatMainFragment newInstance() {
        ChatMainFragment fragment = new ChatMainFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FRIENDS, new ArrayList<Friend>());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Friends");
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(ARG_FRIENDS)) {
                mFriends = (ArrayList<Friend>) args.getSerializable(ARG_FRIENDS);
            }

            //            Initializing Firebase...
            db = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();
            mUser = mAuth.getCurrentUser();

            this.email = mUser.getEmail();

            //            Loading initial data...
            loadData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat_main, container, false);


        //      Setting up RecyclerView........
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        friendsAdapter = new FriendsAdapter(mFriends, getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(friendsAdapter);

        editProfile = rootView.findViewById(R.id.mainChatEditProfileButton);

        editProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMain, new ChatEditProfileFragment())
                        .addToBackStack("edit profile")
                        .commit();
            }
        });

//        Create a listener for Firebase data change...
        db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }else{
//                            retrieving all the elements from Firebase....
                            ArrayList<Friend> newFriends = new ArrayList<>();
                            for(DocumentSnapshot document : value.getDocuments()){
                                newFriends.add(document.toObject(Friend.class));
                            }
//                            replace all the item in the current RecyclerView with the received elements...
                            updateRecyclerView(newFriends);
                        }
                    }
                });

        return rootView;
    }

    public void updateRecyclerView(ArrayList<Friend> friends){
        friends.removeIf(f -> f.getEmail().equals(email));
        friendsAdapter.setUsers(friends);
        friendsAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        ArrayList<Friend> friends = new ArrayList<>();
        db.collection("users")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
//                                Just like GSON..... Friend has to be Serializable,
//                                has to exactly match the variable names with the keys in the documents,
//                                and must have getters, setters, and toString() ....

                                Friend friend = documentSnapshot.toObject(Friend.class);
                                friends.add(friend);

                            }
                            updateRecyclerView(friends);
                        }
                    }
                });
    }
}