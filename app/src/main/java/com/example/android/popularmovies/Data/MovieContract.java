package com.example.android.popularmovies.Data;

import android.provider.BaseColumns;

public class MovieContract {
    public static final class FavoriteMoviesEntry implements BaseColumns {

        public static final String TABLE_NAME = "favorite_movies_table";
        public static final String COLUMN_ID = "movie_id";
        public static final String COLUMN_ORGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_RELEASE_DATE = "date";
        public static final String COLUMN_VOTE_AVERAGE = "average";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_TRAILER = "trailer";
        public static final String COLUMN_REVIEWS = "reviews";
        public static final String COLUMN_BACKDROP_IMG = "backdrop";
    }
    public static final class VideosMovieEntry implements BaseColumns{
        public static final String TABLE_NAME = "videos_movies_table";
        public static final String COLUMN_VID = "video_id";
        public static final String COLUMN_MOVIE = "movie_id";
        public static final String COLUMN_KEY = "key_value";
        public static final String COLUMN_NAME = "name";
    }

    public static final class ReviewsMovieEntry implements BaseColumns{
        public static final String TABLE_NAME = "reviews_movies_table";
        public static final String COLUMN_MOVIE = "movie_id";
        public static final String COLUMN_RID = "review_id";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";
    }
}
