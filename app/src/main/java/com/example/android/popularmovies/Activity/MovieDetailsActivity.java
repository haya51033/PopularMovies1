package com.example.android.popularmovies.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Adapter.MoviesAdapter;
import com.example.android.popularmovies.Adapter.ReviewAdapter;
import com.example.android.popularmovies.Adapter.TrailerAdapter;
import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Models.Reviews;
import com.example.android.popularmovies.Models.ReviewsResponceValue;
import com.example.android.popularmovies.Models.Videos;
import com.example.android.popularmovies.MyAPI.ApiActivity;
import com.example.android.popularmovies.MyAPI.IApi;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by haya on 20/04/2018.
 */

public class MovieDetailsActivity extends AppCompatActivity implements TrailerAdapter.TrailerOnClickHandler,
        ReviewAdapter.ReviewOnClickHandler{
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
    private TextView anyb;
    private TextView anyc;
    private TextView trailerTit;
    private TextView reviewTit;
    public Movie movie;
    public Button saveAsFavoButton;
    TextView tv_MovieTrailer;
    TextView tv_outher;
    TextView tv_content;
    Videos video;
    Reviews reviews;
    TrailerAdapter trailerAdapter;
    ReviewAdapter reviewAdapter;
    ArrayList<Videos> Object1;
    ArrayList<Reviews> Object2;


    RecyclerView trailerRV;
    RecyclerView reviewRV;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        trailerRV = (RecyclerView) findViewById(R.id.rv_trailer);
        reviewRV = (RecyclerView)findViewById(R.id.rv_reviews);

      //  mGridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        trailerRV.setLayoutManager(new LinearLayoutManager(this));
        reviewRV.setLayoutManager(new LinearLayoutManager(this));


        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");

        ArrayList<Movie> Object = (ArrayList<Movie>) args.getSerializable("MovieList");
        if (Object!=null)
        {
            movie = Object.get(0);
        }

        Object1 = (ArrayList<Videos>) args.getSerializable("VideosList");
        if (Object1!=null)
        {
            video = Object1.get(0);
        }

        Object2 = (ArrayList<Reviews>) args.getSerializable("ReviewsList");
        if (Object2!=null)
        {
            reviews = Object2.get(0);
        }




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
        tv_MovieTrailer = (TextView)findViewById(R.id.tv_MovieTrailer);
        tv_content = (TextView)findViewById(R.id.tv_content);
        tv_outher = (TextView)findViewById(R.id.tv_author);
        reviewTit = (TextView)findViewById(R.id.reviewTitle);
        trailerTit = (TextView)findViewById(R.id.trailerTitle);


        if (movie != null)
        {
            mTitleTextView.setText(movie.getTitle());
            mLang.setText(movie.getOriginalLanguage());
            mYearTextView.setText(movie.getReleaseDate().toString());
            mRating.setText(movie.getVoteAverage().toString());
            mOverview.setText(movie.getOverview());

            String img1 = movie.getPosterPath();
            String img2 = movie.getBackdropPath();

            Picasso.with(getApplicationContext()).load(url+img1).into(mPosterImageViewBack);
            Picasso.with(getApplicationContext()).load(url+img2).into(mPosterImageView);

            configureRecyclerView(Object1);
            configureRecyclerView2(Object2);
            saveAsFavoButton = (Button) findViewById(R.id.saveAsFv_button);

            saveAsFavoButton.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Button clicked..", Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    private void configureRecyclerView(ArrayList videos) {
        trailerRV =(RecyclerView) findViewById(R.id.rv_trailer);

        trailerRV.setHasFixedSize(true);
       trailerRV.setLayoutManager(new LinearLayoutManager(this));
        trailerAdapter = new TrailerAdapter(this);
        trailerAdapter.setVideoData(videos);
        trailerRV.setAdapter(trailerAdapter);
    }
    private void configureRecyclerView2(ArrayList reviews) {
        reviewRV =(RecyclerView) findViewById(R.id.rv_reviews);

        reviewRV.setHasFixedSize(true);
        reviewRV.setLayoutManager(new LinearLayoutManager(this));

        reviewAdapter = new ReviewAdapter(this);
        reviewAdapter.setReviewsData(reviews);
        reviewRV.setAdapter(reviewAdapter);
    }


    @Override
    public void onClickVideo(Videos videos) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videos.getKey())));
    }

    @Override
    public void onClickReview(Reviews reviews) {

    }

    /*public void saveAsFavorite(View view)
    {
        Toast.makeText(getApplicationContext(),"Button clicked..", Toast.LENGTH_LONG).show();
    }*/
}
