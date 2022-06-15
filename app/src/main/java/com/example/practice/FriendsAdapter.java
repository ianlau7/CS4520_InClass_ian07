package com.example.practice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

@Keep
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private static final String TAG = "demo";

    private ArrayList<Friend> friends;

    private IfriendsListRecyclerAction mListener;

    public FriendsAdapter() {
    }

    public FriendsAdapter(ArrayList<Friend> friends, Context context) {
        this.friends = friends;
        if(context instanceof IfriendsListRecyclerAction){
            this.mListener = (IfriendsListRecyclerAction) context;
        }else{
            throw new RuntimeException(context.toString()+ "must implement IfriendsListRecyclerAction");
        }

    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void setUsers(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView textViewName;
        private final Button buttonChat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.textFriendMenu);
            this.buttonChat = itemView.findViewById(R.id.buttonChat);
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public Button getButtonChat() {
            return buttonChat;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRecyclerView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.friend_row,parent, false);

        return new ViewHolder(itemRecyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Friend curFriend = this.getFriends().get(position);

        holder.getTextViewName().setText(curFriend.getfirstName() + " " + curFriend.getlastName());

        holder.getButtonChat().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.chatButtonClickedFromRecylcerView(friends.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.getFriends().size();
    }

    public interface IfriendsListRecyclerAction {
        void chatButtonClickedFromRecylcerView(Friend friend);
    }
}
