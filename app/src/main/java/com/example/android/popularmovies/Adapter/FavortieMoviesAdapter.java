package com.example.android.popularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Activity.MovieDetailsActivity;
import com.example.android.popularmovies.Data.MovieContract;
import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavortieMoviesAdapter extends RecyclerView.Adapter<FavortieMoviesAdapter.MovieViewHolder> {

// Holds on to the cursor to display the waitlist
private Cursor mCursor;
private Context mContext;
private MovieOnClickHandler mMovieOnClickHandler;

    public FavortieMoviesAdapter(MovieOnClickHandler movieOnClickHandler) {
        mMovieOnClickHandler = movieOnClickHandler;
    }

    public void setMovieData(Cursor movieData) {
        mCursor = movieData;
        notifyDataSetChanged();
    }
/**
 * Constructor using the context and the db cursor
 * @param context the calling context/activity
 * @param cursor the db cursor with waitlist data to display
 */
public FavortieMoviesAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;

        }



@Override
public FavortieMoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.row_movie, parent, false);
        return new MovieViewHolder(view);
        }

@Override
public void onBindViewHolder(FavortieMoviesAdapter.MovieViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
        return; // bail if returned null

        // Update the view holder with the information needed to display

    String url = "http://image.tmdb.org/t/p/w185/";
    ImageView iv = holder.mImageView;


        String img1 = mCursor.getString(mCursor.getColumnIndex(MovieContract.FavoriteMoviesEntry.COLUMN_POSTER));
        Picasso.with(mContext).load(url+img1).into(iv);
        }


@Override
public int getItemCount() {
        return mCursor.getCount();
        }

// COMPLETED (15) Create a new function called swapCursor that takes the new cursor and returns void
/**
 * Swaps the Cursor currently held in the adapter with a new one
 * and triggers a UI refresh
 *
 * @param newCursor the new cursor that will replace the existing one
 */
public void swapCursor(Cursor newCursor) {
        // COMPLETED (16) Inside, check if the current cursor is not null, and close it if so
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        // COMPLETED (17) Update the local mCursor to be equal to  newCursor
        mCursor = newCursor;
        // COMPLETED (18) Check if the newCursor is not null, and call this.notifyDataSetChanged() if so
        if (newCursor != null) {
        // Force the RecyclerView to refresh
        this.notifyDataSetChanged();
        }
        }

/**
 * Inner class to hold the views needed to display a single item in the recycler-view
 */
public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView mImageView;

    public MovieViewHolder(View itemView) {
        super(itemView);

        mImageView =(ImageView)itemView.findViewById(R.id.iv_movie_poster);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        mCursor.moveToPosition(position);

        int id = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(MovieContract.FavoriteMoviesEntry.COLUMN_ID)));
        String orginalLang =mCursor.getString(mCursor.getColumnIndex(MovieContract.FavoriteMoviesEntry.COLUMN_ORGINAL_LANGUAGE));
        String title = mCursor.getString(mCursor.getColumnIndex(MovieContract.FavoriteMoviesEntry.COLUMN_TITLE));
        String poster = mCursor.getString(mCursor.getColumnIndex(MovieContract.FavoriteMoviesEntry.COLUMN_POSTER));
        String backPoster =mCursor.getString(mCursor.getColumnIndex(MovieContract.FavoriteMoviesEntry.COLUMN_BACKDROP_IMG));
        String releasDate = mCursor.getString(mCursor.getColumnIndex(MovieContract.FavoriteMoviesEntry.COLUMN_RELEASE_DATE));
        Double avreg = Double.parseDouble(mCursor.getString(mCursor.getColumnIndex(MovieContract.FavoriteMoviesEntry.COLUMN_VOTE_AVERAGE)));
        String overview = mCursor.getString(mCursor.getColumnIndex(MovieContract.FavoriteMoviesEntry.COLUMN_OVERVIEW));

        Movie selectedOne = new Movie (id, avreg,poster,orginalLang,title,backPoster,overview,releasDate);
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        movieArrayList.add(selectedOne);


//        mMovieOnClickHandler.onClickMovie(selectedOne);

        Intent intent = new Intent(mContext, MovieDetailsActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("MovieList", movieArrayList);
        intent.putExtra("BUNDLE1", args);
        mContext.startActivity(intent);

    }
}
    public interface MovieOnClickHandler {
        void onClickMovie(Movie movie);
    }
}