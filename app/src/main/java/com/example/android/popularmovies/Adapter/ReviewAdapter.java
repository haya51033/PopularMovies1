package com.example.android.popularmovies.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.Models.Reviews;
import com.example.android.popularmovies.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {
    private Context context;
    private ArrayList<Reviews> reviews;
    private ReviewAdapter.ReviewOnClickHandler mreviewOnClickHandler;


    public ReviewAdapter(ReviewAdapter.ReviewOnClickHandler reviewOnClickHandler) {
        mreviewOnClickHandler = reviewOnClickHandler;
    }

    public void setReviewsData(ArrayList<Reviews> reviewsData) {
        reviews = reviewsData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_review, parent, false);
        // Return a new holder instance
        ReviewAdapter.ReviewAdapterViewHolder viewHolder = new ReviewAdapter.ReviewAdapterViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public int getItemCount() {
        if(reviews == null) {
            return 0;
        }
        return reviews.size();
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewAdapterViewHolder viewHolder, int position) {
        Reviews review = reviews.get(position);

        String outher = review.getAuthor();
        String content = review.getContent();

        TextView tv = viewHolder.tv_auther;
        TextView tv1 = viewHolder.tv_content;
        tv.setText(outher);
        tv1.setText(content);

    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView tv_auther;
        public final TextView tv_content;


        public ReviewAdapterViewHolder(View view){
            super(view);

            tv_auther = (TextView) view.findViewById(R.id.tv_author);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position =  getAdapterPosition();
            Reviews selectedReview = reviews.get(position);
            mreviewOnClickHandler.onClickReview(selectedReview);

        }
    }
    public interface ReviewOnClickHandler {
        void onClickReview(Reviews reviews);
    }
}