package com.example.practice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Keep
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    OkHttpClient client = new OkHttpClient();

    private String token;
    private ArrayList<Note> notes;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView notesTextView;
        private final Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.notesTextView = itemView.findViewById(R.id.noteCardTextView);
            this.delete = itemView.findViewById(R.id.noteCardDeleteButton);
        }

        public Button getDelete() {
            return delete;
        }

        public TextView getNotesTextView() {
            return notesTextView;
        }
    }

    public NotesAdapter (ArrayList<Note> notes, String token) {
        this.notes = notes;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_card_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getNotesTextView().setText(notes.get(position).getText());
        holder.getDelete().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                RequestBody formBody = new FormBody.Builder()
                        .add("id", notes.get(holder.getAdapterPosition()).getId())
                        .build();

                Request request = new Request.Builder()
                        .url("http://dev.sakibnm.space:3000/api/note/delete")
                        .addHeader("x-access-token", token)
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String string = response.body().string();

                            try {
                                JSONObject json = new JSONObject(string);

                                String msg = json.getString("message");

                                if (json.getBoolean("delete")) {

                                    ((in_class_07) v.getContext()).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            notes.remove(holder.getAdapterPosition());
                                            notifyDataSetChanged();
                                        }
                                    });

                                    ((in_class_07) v.getContext()).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(v.getContext(), msg,
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                ((in_class_07) v.getContext()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(v.getContext(), "error with JSON",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } else {
                            ((in_class_07) v.getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(v.getContext(), "message not found",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        ((in_class_07) v.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(v.getContext(), "something went wrong.",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
