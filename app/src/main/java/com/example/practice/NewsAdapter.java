package com.example.practice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<Article> articles;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView, authorTextView, publishedAtTextView, descriptionTextView;
        private final ImageView urlToImageImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTextView = itemView.findViewById(R.id.titleDisplayTextView);
            this.authorTextView = itemView.findViewById(R.id.authorDisplayTextView);
            this.publishedAtTextView = itemView.findViewById(R.id.datePublishedDisplayTextView);
            this.descriptionTextView = itemView.findViewById(R.id.descriptionDisplayTextView);
            this.urlToImageImageView = itemView.findViewById(R.id.newsImageView);
        }

        public ImageView getUrlToImageImageView() {
            return urlToImageImageView;
        }

        public TextView getDescriptionTextView() {
            return descriptionTextView;
        }

        public TextView getPublishedAtTextView() {
            return publishedAtTextView;
        }

        public TextView getAuthorTextView() {
            return authorTextView;
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }
    }

    public NewsAdapter(ArrayList<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTitleTextView().setText(articles.get(position).getTitle());
        holder.getAuthorTextView().setText(articles.get(position).getAuthor());
        holder.getPublishedAtTextView().setText(articles.get(position).getPublishedAt());
        holder.getDescriptionTextView().setText(articles.get(position).getDescription());
        Picasso.get().load(articles.get(position).getUrlToImage()).into(holder.getUrlToImageImageView());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


}
