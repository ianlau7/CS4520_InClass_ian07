package com.example.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

@Keep
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    private static final String TAG = "demo";
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
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
        private final ImageView imageViewMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.textViewName);
            this.textViewMessage = itemView.findViewById(R.id.textViewMessage);
            this.imageViewMessage = itemView.findViewById(R.id.imageViewMessage);
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public TextView getTextViewMessage() {
            return textViewMessage;
        }

        public ImageView getImageViewMessage() {
            return imageViewMessage;
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
        if(currMessage.getType() == 0) {
            holder.getImageViewMessage().setVisibility(View.GONE);
            holder.getTextViewName().setText(currMessage.getSender() + ":");
            holder.getTextViewMessage().setText(currMessage.getMessage());
        } else if (currMessage.getType() == 1) {
            StorageReference imageRef = storageRef.child(currMessage.getMessage());

            final long ONE_MEGABYTE = 1024 * 1024;
            imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.getTextViewName().setText(currMessage.getSender() + ":");
                    holder.getTextViewMessage().setText("");
                    holder.getImageViewMessage().setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return this.getMessages().size();
    }
}
