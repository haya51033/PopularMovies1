package com.example.android.popularmovies.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.MyAPI.ApiActivity;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by haya on 26/08/2017.
 */

public class ImageAdapter extends ArrayAdapter<Movie> {

    String url = "http://image.tmdb.org/t/p/w185/";
    private Context context;
    private List<Movie> movieList;

    public ImageAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
        this.context = context;
        this.movieList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_movie,parent,false);

        Movie movie = movieList.get(position);
        String img1 = movie.getPosterPath();
        ImageView iv = (ImageView) view.findViewById(R.id.iv_movie_poster);
        Picasso.with(getContext()).load(url+img1).into(iv);

        return view;
    }
}
