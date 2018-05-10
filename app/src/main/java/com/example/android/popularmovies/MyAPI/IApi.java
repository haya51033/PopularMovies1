package com.example.android.popularmovies.MyAPI;

import com.example.android.popularmovies.Models.ResponseValue;
import com.example.android.popularmovies.Models.ReviewsResponceValue;
import com.example.android.popularmovies.Models.TrailerResponceValue;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by haya on 20/04/2018.
 */

public interface IApi {

    @GET("3/movie/popular")
    Call<ResponseValue> GetPopular(@Query("api_key") String api_key);

    @GET("3/movie/top_rated")
    Call<ResponseValue> GetRated(@Query("api_key") String api_key);

    @GET("3/movie/{id}/videos")
    Call<TrailerResponceValue> GetVideos( @Path("id") String id, @Query("api_key") String api_key);


    @GET("3/movie/{id}/reviews")
    Call<ReviewsResponceValue> GetReviews(@Path("id") int id, @Query("api_key") String api_key);

}
