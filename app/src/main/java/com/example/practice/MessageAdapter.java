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
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    private static final String TAG = "demo";

    private ArrayList<Message> messages;


    public MessageAdapter() {
    }

    public MessageAdapter(ArrayList<Message> messages, Context context) {
        this.messages = messages;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView textViewName;
        private final TextView textViewMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.textViewName);
            this.textViewMessage = itemView.findViewById(R.id.textViewMessage);
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public TextView getTextViewMessage() {
            return textViewMessage;
        }
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRecyclerView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.message_row,parent, false);

        return new MessageAdapter.ViewHolder(itemRecyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        Message currMessage = this.getMessages().get(position);

        holder.getTextViewName().setText(currMessage.getSender() + ":");
        holder.getTextViewMessage().setText(currMessage.getMessage());
    }

    @Override
    public int getItemCount() {
        return this.getMessages().size();
    }
}
