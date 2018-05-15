package com.example.android.popularmovies.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Models.Videos;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {
    private Context context;
    private ArrayList <Videos> videos;
    private TrailerOnClickHandler mtrailerOnClickHandler;


    public TrailerAdapter(TrailerOnClickHandler trailerOnClickHandler) {
        mtrailerOnClickHandler = trailerOnClickHandler;
    }

    public void setVideoData(ArrayList<Videos> videoData) {
        videos = videoData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_trailer, parent, false);
        // Return a new holder instance
        TrailerAdapterViewHolder viewHolder = new TrailerAdapterViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public int getItemCount() {
        if(videos == null) {
            return 0;
        }
        return videos.size();
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerAdapterViewHolder viewHolder, int position) {
        Videos video = videos.get(position);
       String te = video.getName();
       TextView tv = viewHolder.mTextView;
       tv.setText(te);

    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mTextView;


        public TrailerAdapterViewHolder(View view){
            super(view);

            mTextView = (TextView) view.findViewById(R.id.tv_MovieTrailer);
            view.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            int position =  getAdapterPosition();
            Videos selectedVideo = videos.get(position);
            mtrailerOnClickHandler.onClickVideo(selectedVideo);

        }
    }
    public interface TrailerOnClickHandler {
        void onClickVideo(Videos videos);
    }
}