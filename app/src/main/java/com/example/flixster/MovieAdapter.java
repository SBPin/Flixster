package com.example.flixster;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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

import org.parceler.Parcels;

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

        //  determine current orientation
        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        // build url for poster image
        String imageUrl = null;

        //  if in portrait mode, load poster image
        if (isPortrait) {
            imageUrl = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());

        } else {
            //  if landscape
            imageUrl = config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath());

        }

        //  get the correct placeholder and imageview for the current orientation
        int placeholderId = isPortrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = isPortrait ? viewHolder.ivPosterImage : viewHolder.ivBackDropImage;

        //  load image using glide
        Glide.with(context) //FIX LATER
                .load(imageUrl)
               // .transform(new RoundedCornersTransformation(30, 10))
                .apply(new RequestOptions()
                   //     .transform(new CenterCrop(), new RoundedCorners(30))
                .placeholder(placeholderId)  //  GIF
                .error(placeholderId) )       //  IMAGE DISPLAYED IF DOWNLOADS FAIL
                .into(imageView);

    }

    //  returns the size of entire data set
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //  create the viewholder as a static inner class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //  track view objects
        ImageView ivPosterImage;
        ImageView ivBackDropImage;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //  lookup via objects by id
            ivPosterImage = (ImageView) itemView.findViewById(R.id.imageView);
            ivBackDropImage = (ImageView) itemView.findViewById(R.id.ivBackdrop);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //  get item position
            int position = getAdapterPosition();
            //  checks pos. is valid
            if (position != RecyclerView.NO_POSITION) {
                //  get movie at the position
                Movie movie = movies.get(position);
                //  create intent for new activity
                Intent intent = new Intent (context, MovieDetailsActivity.class);
                //  serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                //  show activity
                context.startActivity(intent);

            }
        }


    }
}


