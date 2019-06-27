package com.example.flixster;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster.models.Config;
import com.example.flixster.models.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    //  list of movies
    ArrayList<Movie> movies;
    //  config needed for image urls
    Config config;
    //  context for rendering
    Context context;

    //  initialize with list

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    //  creates and inflates a new view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //  get the context and create the inflater
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //  create the view using the item_movie layout
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        //  return a new ViewHolder
        return new ViewHolder(movieView);
    }

    //  binds an inflated view to a new item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //  get the movie data at the specified position
        Movie movie = movies.get(i);
        //  populate the view with the movie data
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        // build url for poster image
        String imageUrl = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());

        //  load image using glide
        Glide.with(context) //FIX LATER
                .load(imageUrl)
                .apply(new RequestOptions()
                .placeholder(R.drawable.flicks_movie_placeholder)  //  GIF
                .error(R.drawable.flicks_movie_placeholder) )       //  IMAGE DISPLAYED IF DOWNLOADS FAIL
                .into(viewHolder.ivPosterImage);

    }

    //  returns the size of entire data set
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //  create the viewholder as a static inner class
    public static class ViewHolder extends RecyclerView.ViewHolder {

        //  track view objects
        ImageView ivPosterImage;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //  lookup via objects by id
            ivPosterImage = (ImageView) itemView.findViewById(R.id.imageView);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvView);
        }
    }
}

