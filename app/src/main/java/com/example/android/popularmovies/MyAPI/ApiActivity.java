package com.example.android.popularmovies.MyAPI;

import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmovies.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by haya on 21/02/2018.
 */

public class ApiActivity extends AppCompatActivity{

    public static final String BASE_URL= "https://api.themoviedb.org/";
    private final String api_key= BuildConfig.MY_MOVIE_DB_API_KEY;

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    public Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public String getApi_key() {
        return api_key;
    }
}

