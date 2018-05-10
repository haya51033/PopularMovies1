package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Toast;

import com.example.android.popularmovies.Activity.MovieDetailsActivity;
import com.example.android.popularmovies.Adapter.MoviesAdapter;
import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Models.ResponseValue;
import com.example.android.popularmovies.Models.Reviews;
import com.example.android.popularmovies.Models.ReviewsResponceValue;
import com.example.android.popularmovies.Models.TrailerResponceValue;
import com.example.android.popularmovies.Models.Videos;
import com.example.android.popularmovies.MyAPI.ApiActivity;
import com.example.android.popularmovies.MyAPI.IApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieOnClickHandler{
    ApiActivity rB = new ApiActivity();
    IApi service =rB.retrofit.create(IApi.class);
    MoviesAdapter moviesAdapter;
    ArrayList<Movie> arrayList;
    ArrayList<Videos> arrayList1;


    static final String STRING_VALUE = "string_value";
    static  String ratingSortState="no";
    RecyclerView rvMovies;
    private GridLayoutManager mGridLayoutManager;
    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";
    int positionIndex;
    int  lastFirstVisiblePosition;
     List<Videos> videos= new ArrayList<>();
     String movieId="";
    public   ArrayList<Reviews> reviewsArrayList=new ArrayList<>();
    ReviewsResponceValue h = new ReviewsResponceValue();
    List<Reviews> rrr = new ArrayList<>();

    int mID;



    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save custom values into the bundle
        savedInstanceState.putString(STRING_VALUE, ratingSortState);
        savedInstanceState.putParcelable(BUNDLE_RECYCLER_LAYOUT,
                mGridLayoutManager.onSaveInstanceState());

        lastFirstVisiblePosition = ((GridLayoutManager)rvMovies.getLayoutManager())
                .findFirstCompletelyVisibleItemPosition();
        savedInstanceState.putInt("INT_VALUE",lastFirstVisiblePosition);
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        ratingSortState = savedInstanceState.getString(STRING_VALUE);
        positionIndex = savedInstanceState.getInt("INT_VALUE");

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvMovies = (RecyclerView) findViewById(R.id.rv_movies);
        mGridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);

        if(savedInstanceState != null)
        {
            if (ratingSortState.equals("yes"))
            {
                sortByTopRating();

            }
            else if (ratingSortState.equals("no"))
            {
                sortByMostPopular();
            }
        }
        else
            {
                sortByMostPopular();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_sortPopular:
              sortByMostPopular();
                return true;
            case R.id.action_sortRating:
               sortByTopRating();
                return true;
            default:
               return super.onOptionsItemSelected(item);
        }
    }

   public void sortByMostPopular()
   {
       Call<ResponseValue> call = service.GetPopular(rB.getApi_key().toString());
       call.enqueue(new Callback<ResponseValue>() {
           @Override
           public void onResponse(Call<ResponseValue> call, Response<ResponseValue> response) {

               if(response.isSuccessful())
               {
                   ResponseValue responseValue=response.body();
                   if(responseValue != null)
                   {
                       final List<Movie> movies =responseValue.getMovies();
                       setContentView(R.layout.activity_main);

                       arrayList = new ArrayList<>();//create a list to store the objects
                       arrayList.addAll(movies);

                       configureRecyclerView(arrayList);

                       Toast.makeText(getApplicationContext(),"Sorted By the Most Popular",Toast.LENGTH_LONG).show();
                       ratingSortState="no";
                   }
                   else
                   {
                       Toast.makeText(getApplicationContext(),"No Movies To show..", Toast.LENGTH_LONG).show();
                   }
               }
           }

           @Override
           public void onFailure(Call<ResponseValue> call, Throwable t) {
               Toast.makeText(getApplicationContext(),"Failed Connection..", Toast.LENGTH_LONG).show();
           }
       });
   }

   private void configureRecyclerView(ArrayList movies) {
       rvMovies = (RecyclerView) findViewById(R.id.rv_movies);
       rvMovies.setHasFixedSize(true);
       rvMovies.setLayoutManager( new GridLayoutManager(getApplicationContext(), 3));
       moviesAdapter = new MoviesAdapter(this);
       moviesAdapter.setMovieData(movies);
       rvMovies.setAdapter(moviesAdapter);
       ((GridLayoutManager)rvMovies.getLayoutManager()).scrollToPosition(positionIndex);
   }

    @Override
    public void onClickMovie(Movie movie) {

        final ArrayList<Movie> movieArrayList= new ArrayList<>();
        movieArrayList.add(movie);

        final  ArrayList<Videos> videosArrayList = new ArrayList<>();
        movieId = movie.getId().toString();
        Call<TrailerResponceValue> call = service.GetVideos(movieId, rB.getApi_key().toString());
        call.enqueue(new Callback<TrailerResponceValue>() {
            @Override
            public void onResponse(Call<TrailerResponceValue> call, Response<TrailerResponceValue> response) {
                if(response.isSuccessful())
                {
                    TrailerResponceValue trailerResponceValue = response.body();
                    if(trailerResponceValue != null)
                    {
                        videos =trailerResponceValue.getVideos();
                        arrayList1 = new ArrayList<>();//create a list to store the objects
                        arrayList1.addAll(videos);
                        mID =  Integer.parseInt(movieId);

                        Call<ReviewsResponceValue> call2 = service.GetReviews(mID,rB.getApi_key().toString());
                        call2.enqueue(new Callback<ReviewsResponceValue>() {
                            @Override
                            public void onResponse(Call<ReviewsResponceValue> call, Response<ReviewsResponceValue> response) {
                                if(response.isSuccessful())
                                {
                                    h = response.body();
                                    if(h!=null) {
                                        rrr = h.getResults();
                                        reviewsArrayList.addAll(rrr);

                                        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                                        Bundle args = new Bundle();
                                        args.putSerializable("MovieList", movieArrayList);
                                        args.putSerializable("VideosList", arrayList1);
                                        args.putSerializable("ReviewsList", reviewsArrayList);
                                        intent.putExtra("BUNDLE", args);
                                        startActivity(intent);
                                    }
                                    else
                                        {
                                            Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                                            Bundle args = new Bundle();
                                            args.putSerializable("MovieList", movieArrayList);
                                            args.putSerializable("VideosList", arrayList1);
                                            intent.putExtra("BUNDLE", args);
                                            startActivity(intent);
                                        }
                                }
                            }

                            @Override
                            public void onFailure(Call<ReviewsResponceValue> call, Throwable t) {

                                Toast.makeText(getApplicationContext(),"Server Down..", Toast.LENGTH_LONG).show();


                            }
                        });



                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Movies To show..", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<TrailerResponceValue> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Server Down", Toast.LENGTH_LONG).show();

            }
        });
    }


   public  void sortByTopRating()
   {
       Call<ResponseValue> call = service.GetRated(rB.getApi_key().toString());
       call.enqueue(new Callback<ResponseValue>() {
           @Override
           public void onResponse(Call<ResponseValue> call, Response<ResponseValue> response) {

               if(response.isSuccessful())
               {
                   ResponseValue responseValue=response.body();
                   if(responseValue != null)
                   {
                       final List<Movie> movies =responseValue.getMovies();
                       setContentView(R.layout.activity_main);

                      ArrayList<Movie> arrayList = new ArrayList<>();//create a list to store the objects
                       arrayList.addAll(movies);

                      configureRecyclerView(arrayList);
                       Toast.makeText(getApplicationContext(),"Sorted By Top Rated",Toast.LENGTH_LONG).show();
                       ratingSortState="yes";
                   }
                   else
                   {
                       Toast.makeText(getApplicationContext(),"No Movies To show..", Toast.LENGTH_LONG).show();
                   }
               }
           }

           @Override
           public void onFailure(Call<ResponseValue> call, Throwable t) {
               Toast.makeText(getApplicationContext(),"Failed Connection..", Toast.LENGTH_LONG).show();
           }
       });
   }
}
