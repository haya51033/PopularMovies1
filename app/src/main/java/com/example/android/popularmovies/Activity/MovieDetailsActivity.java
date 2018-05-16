package com.example.android.popularmovies.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Adapter.ReviewAdapter;
import com.example.android.popularmovies.Adapter.TrailerAdapter;
import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Models.Reviews;
import com.example.android.popularmovies.Models.ReviewsResponceValue;
import com.example.android.popularmovies.Models.TrailerResponceValue;
import com.example.android.popularmovies.Models.Videos;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



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
    ReviewsResponceValue reviewsResponceValue;
    TrailerResponceValue trailer;
    TrailerAdapter trailerAdapter;
    ReviewAdapter reviewAdapter;
    ArrayList<Movie> Object;
    ArrayList<Videos> Object1;
    ArrayList<Reviews> Object2;
    ArrayList<TrailerResponceValue> Object3;
    ArrayList<ReviewsResponceValue> Object4;
    int movieID;
    RecyclerView trailerRV;
    RecyclerView reviewRV;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        trailerRV = (RecyclerView) findViewById(R.id.rv_trailer);
        reviewRV = (RecyclerView)findViewById(R.id.rv_reviews);

        trailerRV.setLayoutManager(new LinearLayoutManager(this));
        reviewRV.setLayoutManager(new LinearLayoutManager(this));

        Object = new ArrayList<>();
        Object1 = new ArrayList<>();
        Object2 = new ArrayList<>();
        Object3 = new ArrayList<>();
        Object4 = new ArrayList<>();

        movie = new Movie();
        video = new Videos();
        trailer = new TrailerResponceValue();
        reviews = new Reviews();
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");



        Object1 = (ArrayList<Videos>) args.getSerializable("VideosList");
        if (Object1.size() != 0)
        {
            video = Object1.get(0);
            trailerTit = (TextView)findViewById(R.id.trailerTitle);
        }
         Object2 = (ArrayList<Reviews>) args.getSerializable("ReviewsList");
        if (Object2.size() !=0)
        {
            reviews = Object2.get(0);
            reviewTit = (TextView)findViewById(R.id.reviewTitle);
        }

        Object3 = (ArrayList<TrailerResponceValue>) args.getSerializable("TrailerResult");
         if(Object3.size() !=0)
         {
             trailer = Object3.get(0);
         }

        Object = (ArrayList<Movie>) args.getSerializable("MovieList");
        if (Object.size() != 0)
        {
            movie = Object.get(0);
        }

        Object4 = (ArrayList<ReviewsResponceValue>) args.getSerializable("ReviewResultList");
        if(Object4.size() != 0)
        {
            reviewsResponceValue = Object4.get(0);
        }

        movieID = movie.getId();

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

            ArrayList<Videos> tes = new ArrayList<>();
            tes.addAll(Object1);
            configureRecyclerView(tes);
            configureRecyclerView2(Object2);




            /*
            Realm.init(getApplicationContext());
            final Realm realm33 = Realm.getDefaultInstance();
            realm33.beginTransaction();
            Movie movie3 = realm33.where(Movie.class).equalTo("id",movieID).findFirst();
            realm33.commitTransaction();
            saveAsFavoButton = (Button) findViewById(R.id.saveAsFv_button);


            if(movie3 != null)
            {
                saveAsFavoButton.setVisibility(View.GONE);
            }
            else {
                saveAsFavoButton.setOnClickListener( new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                       try
                       {
                           // save movie info
                           Realm.init(getApplicationContext());
                           Realm realm;
                           realm = Realm.getDefaultInstance();
                           realm.beginTransaction();
                           Movie movie1 = movie;
                           if (movie1 != null) {
                               realm.copyToRealm(movie1);
                           }
                           realm.commitTransaction();


                           Realm realm11;
                           realm11 = Realm.getDefaultInstance();
                           realm11.beginTransaction();
                           TrailerResponceValue trailerResponceValue1 = trailer;
                           if(trailerResponceValue1 != null)
                           {
                               realm11.copyToRealm(trailerResponceValue1);
                           }
                           realm11.commitTransaction();


                           Realm realm9;
                           realm9 = Realm.getDefaultInstance();
                           realm9.beginTransaction();
                           ReviewsResponceValue reviewsResponceValue1 = reviewsResponceValue;
                           if (reviewsResponceValue1 != null) {
                               realm9.copyToRealm(reviewsResponceValue1);

                           }
                           realm9.commitTransaction();

                       }
                       catch (Exception e)
                       {
                           Toast.makeText(getApplicationContext(),"Error saving..", Toast.LENGTH_LONG).show();

                       }
                       finally {
                           Toast.makeText(getApplicationContext(),"Movie saved successfully..", Toast.LENGTH_LONG).show();
                       }
                    }
                });



            }*/

        }
        Button shareButton = (Button) findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Popular Movie");
                    String sAux = "\nLet me recommend you this Movie: \n\n "+ movie.getTitle()+"\n\n";
                    sAux = sAux + "http://www.youtube.com/watch?v=" + Object1.get(0).getKey() +"\n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch(Exception e) {
                    //
                }
            }
        });
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


}
