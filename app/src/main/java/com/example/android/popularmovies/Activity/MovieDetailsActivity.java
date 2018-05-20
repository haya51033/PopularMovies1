package com.example.android.popularmovies.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.android.popularmovies.Data.MovieContract;
import com.example.android.popularmovies.Data.MoviesDB;
import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Models.Reviews;
import com.example.android.popularmovies.Models.ReviewsResponceValue;
import com.example.android.popularmovies.Models.TrailerResponceValue;
import com.example.android.popularmovies.Models.Videos;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by haya on 20/04/2018.
 */

public class MovieDetailsActivity extends AppCompatActivity implements TrailerAdapter.TrailerOnClickHandler,
        ReviewAdapter.ReviewOnClickHandler{
    private TextView mTitleTextView,mYearTextView, mRating, mOverview, mLang, mtest,
            mtest1, mtest2, mtest3, mtest4, anyb, anyc, trailerTit, reviewTit;
    TextView tv_MovieTrailer, tv_outher, tv_content;

    private ImageView mPosterImageViewBack,mPosterImageView;
    public Movie movie; public Button saveAsFavoButton;
    Videos video; Reviews reviews;
    ReviewsResponceValue reviewsResponceValue; TrailerResponceValue trailer;
    TrailerAdapter trailerAdapter; ReviewAdapter reviewAdapter;
    ArrayList<Movie> Object; ArrayList<Videos> Object1;
    ArrayList<Reviews> Object2; ArrayList<TrailerResponceValue> Object3;
    ArrayList<ReviewsResponceValue> Object4; int movieID;
    RecyclerView trailerRV; RecyclerView reviewRV;

    public ArrayList<Videos> trailerVideos= new ArrayList<>();
    public ArrayList<Reviews> movieReviews = new ArrayList<>();


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
        final Bundle args = intent.getBundleExtra("BUNDLE");

        Bundle args1 = intent.getBundleExtra("BUNDLE1");
        if(args1!= null)
        {
            Object = (ArrayList<Movie>) args1.getSerializable("MovieList");
            if (Object.size() != 0)
            {
                movie = Object.get(0);
            }
        }

        if (args != null)
        {
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

            if(args !=null)
            {
                ArrayList<Videos> tes = new ArrayList<>();
                tes.addAll(Object1);
                configureRecyclerView(tes);
                configureRecyclerView2(Object2);
            }
            if(args == null)
            {
                ArrayList<Videos> videosArrayList1 =new ArrayList<>();
                getAllTrailer(movieID).toArray();
                videosArrayList1.addAll(trailerVideos);
                configureRecyclerView(videosArrayList1);

                ArrayList<Reviews> reviewsArrayList1 = new ArrayList<>();
                getAllReview(movieID).toArray();
                reviewsArrayList1.addAll(movieReviews);
                configureRecyclerView2(reviewsArrayList1);
            }


            saveAsFavoButton = (Button)findViewById(R.id.saveAsFv_button);
            boolean exist = getMovie(movie.getId());
            if(exist == true)
            {
                saveAsFavoButton.setVisibility(View.GONE);
            }

            else
            {
                saveAsFavoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Movie mm = movie;
                        ArrayList <Videos> vv = new ArrayList<>();
                        ArrayList <Reviews> rr = new ArrayList<>();

                        try {
                            addNewMovie(mm);
                            if(args != null){
                                if(Object1.size()>0)
                                {
                                    vv.addAll(Object1);
                                    addTrailers(mm.getId(),vv);
                                }
                                if(Object2.size()>0)
                                {
                                    rr.addAll(Object2);
                                    addReviews(mm.getId(),rr);
                                }
                            }
                            Toast.makeText(getApplicationContext(),"Movie saved successfully..", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e)
                        {
                            String msg = e.getMessage();
                            Toast.makeText(getApplicationContext(),"Error saving.. " + msg, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
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
    private void addNewMovie(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.FavoriteMoviesEntry.COLUMN_ID,movie.getId());
        cv.put(MovieContract.FavoriteMoviesEntry.COLUMN_BACKDROP_IMG,movie.getBackdropPath());
        cv.put(MovieContract.FavoriteMoviesEntry.COLUMN_ORGINAL_LANGUAGE,movie.getOriginalLanguage());
        cv.put(MovieContract.FavoriteMoviesEntry.COLUMN_OVERVIEW,movie.getOverview());
        cv.put(MovieContract.FavoriteMoviesEntry.COLUMN_POSTER,movie.getPosterPath());
        cv.put(MovieContract.FavoriteMoviesEntry.COLUMN_RELEASE_DATE,movie.getReleaseDate());
        cv.put(MovieContract.FavoriteMoviesEntry.COLUMN_TITLE,movie.getTitle());
        cv.put(MovieContract.FavoriteMoviesEntry.COLUMN_VOTE_AVERAGE,movie.getVoteAverage());

        Uri uri = getContentResolver().insert(MovieContract.FavoriteMoviesEntry.CONTENT_URI, cv);

       /* if (uri != null)
        {
            Toast.makeText(getApplicationContext(),uri.toString(),Toast.LENGTH_LONG).show();
        }*/

        finish();
    }


    private void addTrailers(int movie_id, ArrayList<Videos> videosList)
    {
        ContentValues[] cvArr = new ContentValues[videosList.size()];
        long insertCount = 0;
        int i = 0;
        for (Videos v : videosList) {
            ContentValues cv = new ContentValues();
            cv.put(MovieContract.VideosMovieEntry.COLUMN_VID, v.getId());
            cv.put(MovieContract.VideosMovieEntry.COLUMN_MOVIE, movie_id);
            cv.put(MovieContract.VideosMovieEntry.COLUMN_NAME,v.getName());
            cv.put(MovieContract.VideosMovieEntry.COLUMN_KEY,v.getKey());
            cvArr[i++] = cv;
        }

        insertCount = getApplicationContext().getContentResolver()
                .bulkInsert(MovieContract.VideosMovieEntry.CONTENT_URI, cvArr);
        /*if (insertCount != 0)
        {
            Toast.makeText(getApplicationContext(),String.valueOf(insertCount),Toast.LENGTH_LONG).show();
        }*/
    }


    private void addReviews(int movie_id, ArrayList<Reviews> reviewsArrayList){
        ContentValues[] cvArr = new ContentValues[reviewsArrayList.size()];
        long insertCount = 0;
        int i = 0;
        for (Reviews r : reviewsArrayList) {
            ContentValues cv = new ContentValues();
            cv.put(MovieContract.ReviewsMovieEntry.COLUMN_RID, r.getId());
            cv.put(MovieContract.ReviewsMovieEntry.COLUMN_MOVIE,movie_id);
            cv.put(MovieContract.ReviewsMovieEntry.COLUMN_AUTHOR,r.getAuthor());
            cv.put(MovieContract.ReviewsMovieEntry.COLUMN_CONTENT, r.getContent());

            cvArr[i++] = cv;
        }
        insertCount = getApplicationContext().getContentResolver()
                .bulkInsert(MovieContract.ReviewsMovieEntry.CONTENT_URI, cvArr);
       /* if (insertCount != 0)
        {
            Toast.makeText(getApplicationContext(),String.valueOf(insertCount),Toast.LENGTH_LONG).show();
        }*/
    }

    private boolean getMovie(int id){
       /* String unreadquery="SELECT * FROM "+MovieContract.FavoriteMoviesEntry.TABLE_NAME +
                " WHERE "+ MovieContract.FavoriteMoviesEntry.COLUMN_ID+"="+ id;*/
        Cursor  cur =
                getContentResolver().query(
                        MovieContract.FavoriteMoviesEntry.CONTENT_URI,
                        null,
                        MovieContract.FavoriteMoviesEntry.COLUMN_ID + "='" + id + "'",
                        null,
                        MovieContract.FavoriteMoviesEntry.COLUMN_TITLE);

        if(cur.getCount()>0)
        {
            return true;
        }
        else return false;
    }

    public List<Videos> getAllTrailer(int movie_id){

        Cursor  cursor =
                getContentResolver().query(
                        MovieContract.VideosMovieEntry.CONTENT_URI,
                        null,
                        MovieContract.VideosMovieEntry.COLUMN_MOVIE + "='" + movie_id + "'",
                        null,
                        MovieContract.VideosMovieEntry.COLUMN_NAME);

        if(cursor.moveToFirst()){
            do
            {
                Videos videosObj = new Videos();
                videosObj.setId(String.valueOf(cursor.getInt(1)));
                videosObj.setKey(cursor.getString(3));
                videosObj.setName(cursor.getString(4));

                trailerVideos.add(videosObj);
            } while (cursor.moveToNext());
        }
        // mDb1.close();
        return trailerVideos;
    }

    public List<Reviews> getAllReview(int movie_id){
       /* String getReviews= "SELECT * FROM "+MovieContract.ReviewsMovieEntry.TABLE_NAME +
                " WHERE "+ MovieContract.ReviewsMovieEntry.COLUMN_MOVIE+"="+ movie_id;*/
        Cursor  cursor =
                getContentResolver().query(
                        MovieContract.ReviewsMovieEntry.CONTENT_URI,
                        null,
                        MovieContract.ReviewsMovieEntry.COLUMN_MOVIE + "='" + movie_id + "'",
                        null,
                        null);

        if(cursor.moveToFirst()){
            do{
                Reviews reviewsObject = new Reviews();
                reviewsObject.setId(cursor.getString(1));
                reviewsObject.setAuthor(cursor.getString(3));
                reviewsObject.setContent(cursor.getString(4));

                movieReviews.add(reviewsObject);
            }while (cursor.moveToNext());
        }
        return movieReviews;
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
