package com.example.android.popularmovies.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by haya on 20/04/2018.
 */

public class MovieDetailsActivity extends AppCompatActivity implements Serializable{
    private TextView mTitleTextView;
    private ImageView mPosterImageViewBack;
    private ImageView mPosterImageView;
    private TextView mYearTextView;
    private TextView mRating;
    private TextView mOverview;
    private TextView mLang;
    private TextView mtest;
    private TextView mtest1;
    private TextView mtest2;
    private TextView mtest3;
    private TextView mtest4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Movie> Object = (ArrayList<Movie>) args.getSerializable("MovieList");
        Movie movie = Object.get(0);

        String url = "http://image.tmdb.org/t/p/original/";
        mTitleTextView = (TextView)findViewById(R.id.tv_movie_title);
        mLang = (TextView)findViewById(R.id.tv_movie_lang);
        mYearTextView = (TextView)findViewById(R.id.tv_movie_year);
        mRating = (TextView)findViewById(R.id.tv_movie_rating);
        mOverview = (TextView)findViewById(R.id.tv_movie_overview);
        mPosterImageView = (ImageView) findViewById(R.id.iv_movie_poster1);
        mPosterImageViewBack = (ImageView) findViewById(R.id.iv_movie_poster_back);
        mtest =(TextView)findViewById(R.id.test);
        mtest1 = (TextView)findViewById(R.id.test1);
        mtest2 =(TextView)findViewById(R.id.test2);
        mtest3 =(TextView)findViewById(R.id.test3);
        mtest4 =(TextView)findViewById(R.id.test4);

        if (movie != null)
        {
            mTitleTextView.setText(movie.getTitle());
            mLang.setText(movie.getOriginalLanguage());
            mYearTextView.setText(movie.getReleaseDate().toString());
            mRating.setText(movie.getVoteAverage().toString());
            mOverview.setText(movie.getOverview());

            String img1 = movie.getPosterPath();
            String img2 = movie.getBackdropPath();

            Picasso.with(getApplicationContext()).load(url+img1).into(mPosterImageView);
            Picasso.with(getApplicationContext()).load(url+img2).into(mPosterImageViewBack);

        }
    }
}
