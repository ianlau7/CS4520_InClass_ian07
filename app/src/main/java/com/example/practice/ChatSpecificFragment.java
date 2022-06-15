package com.example.practice;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Keep
public class ChatSpecificFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "friend";

    private Friend friend;
    Button buttonSend;
    ImageButton buttonBack;
    EditText editTextMessage;
    private FirebaseFirestore db;
    String message, username;
    private IspecificFragmentAction mListener;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private ArrayList<Message> mMessages = new ArrayList<>();
    public ChatSpecificFragment() {
        // Required empty public constructor
    }

    public static ChatSpecificFragment newInstance(Friend currFriend) {
        ChatSpecificFragment fragment = new ChatSpecificFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, currFriend);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        if (getArguments() != null) {
            friend = (Friend) getArguments().getSerializable(ARG_PARAM1);
            loadData();
        }
        getActivity().setTitle(friend.getfirstName() + " " + friend.getlastName());
    }

    private void loadData() {
        ArrayList<Message> messages = new ArrayList<>();
        db.collection("users")
                .document(mAuth.getCurrentUser().getDisplayName())
                .collection(friend.getUsername())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
//                                Just like GSON..... Friend has to be Serializable,
//                                has to exactly match the variable names with the keys in the documents,
//                                and must have getters, setters, and toString() ....

                                Message message = documentSnapshot.toObject(Message.class);
                                messages.add(message);

                            }
                            updateRecyclerView(messages);
                        }
                    }
                });
    }

    public void updateRecyclerView(ArrayList<Message> messages) {
        mMessages = messages;
        messageAdapter.setMessages(messages);
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IspecificFragmentAction){
            this.mListener = (IspecificFragmentAction) context;
        }else{
            throw new RuntimeException(context.toString()
                    + "must implement RegisterRequest");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSend) {
            Log.d("demo", "onClick: ");
            message = editTextMessage.getText().toString().trim();
            username = mAuth.getCurrentUser().getDisplayName();
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("sender", username);
            messageData.put("message", message);
            db.collection("users").document(username)
                    .collection(friend.getUsername())
                    .document(mMessages.size() + "")
                    .set(messageData);
            db.collection("users").document(friend.getUsername())
                    .collection(username)
                    .document(mMessages.size() + "")
                    .set(messageData);
            editTextMessage.setText("");
        } else if(v.getId() == R.id.buttonBack) {
            mListener.exitChat();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_specific, container, false);
        buttonSend = view.findViewById(R.id.buttonSend);
        buttonBack = view.findViewById(R.id.buttonBack);
        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonSend.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        //      Setting up RecyclerView........
        recyclerView = view.findViewById(R.id.recyclerViewChat);
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        messageAdapter = new MessageAdapter(mMessages, getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(messageAdapter);




//        Create a listener for Firebase data change...
        db.collection("users")
                .document(mAuth.getCurrentUser().getDisplayName())
                .collection(friend.getUsername())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }else{
//                            retrieving all the elements from Firebase....
                            ArrayList<Message> newMessages = new ArrayList<>();
                            for(DocumentSnapshot document : value.getDocuments()){
                                newMessages.add(document.toObject(Message.class));
                            }
//                            replace all the item in the current RecyclerView with the received elements...
                            updateRecyclerView(newMessages);
                        }
                    }
                });

        return view;
    }

    public interface IspecificFragmentAction {
        void exitChat();
    }
}