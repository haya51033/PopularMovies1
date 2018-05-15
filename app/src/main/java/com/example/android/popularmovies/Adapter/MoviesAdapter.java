package com.example.android.popularmovies.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.MainActivity;
import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends
        RecyclerView.Adapter<MoviesAdapter.MovieAdapterViewHolder> {
    private Context context;
    private ArrayList<Movie> mMovies;
    private MovieOnClickHandler mMovieOnClickHandler;

    public MoviesAdapter(MovieOnClickHandler movieOnClickHandler) {
        mMovieOnClickHandler = movieOnClickHandler;
    }


    public void setMovieData(ArrayList<Movie> movieData) {
        mMovies = movieData;
        notifyDataSetChanged();
    }

    @Override
    public MoviesAdapter.MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_movie, parent, false);

        // Return a new holder instance
        MovieAdapterViewHolder viewHolder = new MovieAdapterViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MovieAdapterViewHolder viewHolder, int position) {
        Movie movie = mMovies.get(position);
        String url = "http://image.tmdb.org/t/p/w185/";
        String img1 = movie.getPosterPath();
        ImageView iv = viewHolder.mImageView;
        Picasso.with(context).load(url+img1).into(iv);

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        if(mMovies == null) {
            return 0;
        }
        return mMovies.size();
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.iv_movie_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movie selectedMovie = mMovies.get(position);
            mMovieOnClickHandler.onClickMovie(selectedMovie);

        }
    }

    public interface MovieOnClickHandler {
        void onClickMovie(Movie movie);
    }

    }