package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @NotNull
    @Override
    // inflates layout
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View newView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(newView);
    }

    @Override
    //populates data
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // get movie pos
        Movie movie = movies.get(position);
        // bind in holder
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    // new view holder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // member elements
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            // find the ids to the elements
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);

            // add this as the itemView's OnClickListener
            itemView.setOnClickListener(this);
        }

        // when the user clicks on a row, show MovieDetailsActivity for the selected movie
        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }
        }

        public void bind(Movie movie) {
            int radius = 20;
            int margin = 0;
            int width = 200;
            int height = 300;
            String imageUrl;

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                width = 300;
                height = 200;
                // placeholder for poster image before loading it
                Glide.with(context)
                        .load("")
                        .placeholder(R.drawable.flicks_backdrop_placeholder)
                        .override(width, height)
                        .centerInside()
                        .transform(new RoundedCornersTransformation(radius, margin))
                        .into(ivPoster);
                imageUrl = movie.getBackdropPath();
            }else{
                // placeholder for poster image before loading it
                Glide.with(context)
                        .load("")
                        .override(width, height)
                        .centerInside()
                        .placeholder(R.drawable.flicks_movie_placeholder)
                        .into(ivPoster);
                imageUrl = movie.getPosterPath();
            }

            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            // to show placeholder works -> internet cutting out does not work
            /* Handler handler = new Handler();
            int h = height;
            int w = width;
            handler.postDelayed(new Runnable() {
                public void run() {
                    // use glide
                    Glide.with(context)
                            .load(imageUrl)
                            .override(w, h)
                            .centerCrop()
                            .transform(new RoundedCornersTransformation(radius, margin))
                            .into(ivPoster);
                }

            }, 500); // 1/2 second delay */

            // use glide
            Glide.with(context)
                    .load(imageUrl)
                    .override(width, height)
                    .centerInside()
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivPoster);



        }
    }
}
