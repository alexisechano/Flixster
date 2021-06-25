package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    // the movie to display
    Movie movie;

    // the view objects
    TextView tvTitle;
    TextView tvOverview;
    TextView tvReleaseDate;
    RatingBar rbVoteAverage;
    ImageView ivTrailer;

    // movie id
    String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // resolve the view objects
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        tvReleaseDate = (TextView) findViewById(R.id.tvDate);
        ivTrailer = (ImageView) findViewById(R.id.ivTrailer);

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // get my API Keys
        String MOVIE_API_KEY = getString(R.string.movie_api_key);

        // API call to GET the video information
        String VIDEO_INFO_URL = "https://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?api_key=" + MOVIE_API_KEY;

        // create HTTP client
        AsyncHttpClient client = new AsyncHttpClient();

        // call to HTTP
        client.get(VIDEO_INFO_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                // get json object
                JSONObject jsonObj = json.jsonObject;

                // get results
                try {
                    // grabs proper information to get key
                    JSONArray results = jsonObj.getJSONArray("results");
                    JSONObject movieInfo = results.getJSONObject(0);
                    videoId = movieInfo.getString("key");

                } catch (JSONException e) {
                    Log.e("MovieDetailsActivity", "Hit JSON Exception");
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d("MovieDetailsActivity", "onFailure");
            }
        });

        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvReleaseDate.setText(movie.getReleaseDate());

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);

        Glide.with(this)
                .load(movie.getBackdropPath())
                .into(ivTrailer);

        ivTrailer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // actually do the youtube video items
                        if(videoId != null){
                            Log.i("MovieDetailsActivity", "Clicked!");
                            // Send message
                            Toast.makeText(getApplicationContext(), "Loading", Toast.LENGTH_SHORT).show();
                            // create intent for the new activity
                            Intent intent = new Intent(getApplicationContext(), MovieTrailerActivity.class);
                            // serialize the movie using parceler, use its short name as a key
                            intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(videoId));
                            // show the activity
                            startActivity(intent);
                        }else{
                            Log.e("MovieDetailsActivity", "No video key found, exiting");
                        }
                    }

                });

    }
}