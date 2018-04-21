package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.popularmovies.Activity.MovieDetailsActivity;
import com.example.android.popularmovies.Adapter.ImageAdapter;
import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Models.ResponseValue;
import com.example.android.popularmovies.MyAPI.ApiActivity;
import com.example.android.popularmovies.MyAPI.IApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiActivity rB = new ApiActivity();
    IApi service =rB.retrofit.create(IApi.class);
    ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sortByMostPopular();
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

                       GridView gridview = (GridView) findViewById(R.id.gridview);

                       ArrayList<Movie> arrayList = new ArrayList<>();//create a list to store the objects
                       arrayList.addAll(movies);

                       imageAdapter = new ImageAdapter(getApplicationContext(),R.layout.row_movie,arrayList);
                       gridview.setAdapter(imageAdapter);
                       Toast.makeText(getApplicationContext(),"Sorted By the Most Popular",Toast.LENGTH_LONG).show();

                       gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                           @Override
                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                               Movie movie1 = imageAdapter.getItem(position);
                               final ArrayList<Movie> mm= new ArrayList<>();

                               mm.add(movie1);
                               Intent intent = new Intent(getApplicationContext(),MovieDetailsActivity.class);
                               Bundle args = new Bundle();
                               args.putSerializable("MovieList",mm);
                               intent.putExtra("BUNDLE",args);
                               startActivity(intent);
                           }
                       });
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

                       GridView gridview = (GridView) findViewById(R.id.gridview);

                       ArrayList<Movie> arrayList = new ArrayList<>();//create a list to store the objects
                       arrayList.addAll(movies);

                       imageAdapter = new ImageAdapter(getApplicationContext(),R.layout.row_movie,arrayList);
                       gridview.setAdapter(imageAdapter);
                       Toast.makeText(getApplicationContext(),"Sorted By Top Rated",Toast.LENGTH_LONG).show();

                       gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                           @Override
                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                               Movie movie1 = imageAdapter.getItem(position);
                               final ArrayList<Movie> mm= new ArrayList<>();

                               mm.add(movie1);
                               Intent intent = new Intent(getApplicationContext(),MovieDetailsActivity.class);
                               Bundle args = new Bundle();
                               args.putSerializable("MovieList",mm);
                               intent.putExtra("BUNDLE",args);
                               startActivity(intent);
                           }
                       });
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
