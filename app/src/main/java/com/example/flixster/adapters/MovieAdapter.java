package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

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
            String imageUrl;
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
                Glide.with(context)
                        .load("https://courses.codepath.com/course_files/android_university_fast_track/assets/flicks_backdrop_placeholder.gif")
                        .placeholder(R.drawable.flicks_backdrop_placeholder)
                        .override(300, 200)
                        .into(ivPoster);
            }else{
                imageUrl = movie.getPosterPath();
                // placeholder for poster image
                Glide.with(context)
                        .load("https://courses.codepath.com/course_files/android_university_fast_track/assets/flicks_movie_placeholder.gif")
                        .override(200, 300)
                        .placeholder(R.drawable.flicks_movie_placeholder)
                        .into(ivPoster);
            }



            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            // use glide
            Glide.with(context).load(imageUrl).into(ivPoster);
        }
    }
}
