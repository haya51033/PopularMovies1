package com.example.android.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.android.popularmovies.Activity.MovieDetailsActivity;
import com.example.android.popularmovies.Adapter.FavortieMoviesAdapter;
import com.example.android.popularmovies.Adapter.MoviesAdapter;
import com.example.android.popularmovies.Data.MovieContract;
import com.example.android.popularmovies.Data.MoviesDB;
import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Models.ResponseValue;
import com.example.android.popularmovies.Models.Reviews;
import com.example.android.popularmovies.Models.ReviewsResponceValue;
import com.example.android.popularmovies.Models.TrailerResponceValue;
import com.example.android.popularmovies.Models.Videos;
import com.example.android.popularmovies.MyAPI.ApiActivity;
import com.example.android.popularmovies.MyAPI.IApi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  implements FavortieMoviesAdapter.MovieOnClickHandler, MoviesAdapter.MovieOnClickHandler
        {
    ApiActivity rB = new ApiActivity();
    IApi service =rB.retrofit.create(IApi.class);
    MoviesAdapter moviesAdapter;
    ArrayList<Movie> arrayList;
    ArrayList<Videos> arrayList1;
    ArrayList<TrailerResponceValue> trailerResultList = new ArrayList<>();
    final ArrayList<ReviewsResponceValue> reviewResult = new ArrayList<>();
    boolean connection;

    static final String STRING_VALUE = "string_value";
    static  String SortState="Popular";
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

    private SQLiteDatabase mDb;
    FavortieMoviesAdapter favortieMoviesAdapter;



    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save custom values into the bundle
        savedInstanceState.putString(STRING_VALUE, SortState);
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
        SortState = savedInstanceState.getString(STRING_VALUE);
        positionIndex = savedInstanceState.getInt("INT_VALUE");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvMovies = (RecyclerView) findViewById(R.id.rv_movies);
        mGridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);

        new Connection().execute("");
        // Create a DB helper (this will create the DB if run for the first time)
        MoviesDB dbHelper = new MoviesDB(this);

        // Keep a reference to the mDb until paused or killed. Get a writable database
        // because you will be adding restaurant customers
        mDb = dbHelper.getWritableDatabase();

        // Get all guest info from the database and save in a cursor
     //   Cursor cursor = getAllGuests();
       // int x;

        if(savedInstanceState != null)
        {
            if (SortState.equals("Top"))
            {
                sortByTopRating();

            }
            else if (SortState.equals("Popular"))
            {
                sortByMostPopular();
            }
            else if (SortState.equals("Favorite"))
            {
                sortByFavorite();
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
            case R.id.action_sortFavorite:
                sortByFavorite();
                return true;
            default:
               return super.onOptionsItemSelected(item);
        }
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
        new Connection().execute("");// test connection
        mID=movie.getId();

        Cursor cursor1 = getAllGuests();

       if(connection == true) // if there is internet connection
       {

           movieId = movie.getId().toString();

           Call<TrailerResponceValue> call = service.GetVideos(movieId, rB.getApi_key().toString());
           call.enqueue(new Callback<TrailerResponceValue>() {
               @Override
               public void onResponse(Call<TrailerResponceValue> call, Response<TrailerResponceValue> response) {
                   if(response.isSuccessful())
                   {
                       final TrailerResponceValue trailerResponceValue = response.body();
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

                                           reviewResult.add(h);


                                           trailerResultList = new ArrayList<>();
                                           trailerResultList.add(trailerResponceValue);

                                           Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                                           Bundle args = new Bundle();
                                           args.putSerializable("MovieList", movieArrayList);
                                           args.putSerializable("ReviewResultList", reviewResult);
                                           args.putSerializable("VideosList", arrayList1);
                                           args.putSerializable("ReviewsList", reviewsArrayList);
                                           args.putSerializable("TrailerResult",trailerResultList);
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
       else // if no connection
           {
               Toast.makeText(getApplicationContext(),"Hello", Toast.LENGTH_LONG).show();


               getMovie();
               int jj=1;
           }
    }

    private Cursor getAllGuests() {
            return mDb.query(
                    MovieContract.FavoriteMoviesEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    MovieContract.FavoriteMoviesEntry.COLUMN_TITLE
            );
    }

    private Cursor getMovie(){
        String unreadquery="SELECT * FROM "+MovieContract.FavoriteMoviesEntry.TABLE_NAME +
                " WHERE "+ MovieContract.FavoriteMoviesEntry.COLUMN_ID +"="+ mID;

        Cursor cursor=mDb.rawQuery(unreadquery, null);
        return cursor;

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
                        SortState="Popular";
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

    public void sortByFavorite()
    {
        Cursor cursor = getAllGuests();
        configureRecyclerView1(cursor);
        SortState="Favorite";
    }



    private void configureRecyclerView1(Cursor cursor) {
        rvMovies = (RecyclerView) findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager( new GridLayoutManager(getApplicationContext(), 3));
        favortieMoviesAdapter = new FavortieMoviesAdapter(this, cursor);
        favortieMoviesAdapter.setMovieData(cursor);
        rvMovies.setAdapter(favortieMoviesAdapter);
        favortieMoviesAdapter.swapCursor(getAllGuests());
        ((GridLayoutManager)rvMovies.getLayoutManager()).scrollToPosition(positionIndex);
    }

 /*   @Override
    public void onClickMovie(Cursor cursor) {
        Toast.makeText(getApplicationContext(),"on click cursor.", Toast.LENGTH_LONG).show();

    }*/
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
                       SortState="Top";
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

    private class Connection extends AsyncTask {
        @Override
        protected Object doInBackground(Object... arg0) {
            connection =  isOnline();
            return  null;
        }
    }
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            try {
                URL url = new URL("https://www.google.com");
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(3000);
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    return new Boolean(true);
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
