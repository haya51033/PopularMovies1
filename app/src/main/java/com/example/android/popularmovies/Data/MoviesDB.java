package com.example.android.popularmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoviesDB extends SQLiteOpenHelper {
    // The database name
    private static final String DATABASE_NAME = "Movies.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public MoviesDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVOTIRE_MOVIES_TABLE = "CREATE TABLE " + MovieContract.FavoriteMoviesEntry.TABLE_NAME + " (" +
                MovieContract.FavoriteMoviesEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContract.FavoriteMoviesEntry.COLUMN_ID + " INTEGER NOT NULL, " +
                MovieContract.FavoriteMoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContract.FavoriteMoviesEntry.COLUMN_POSTER + " TEXT, " +
                MovieContract.FavoriteMoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContract.FavoriteMoviesEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                MovieContract.FavoriteMoviesEntry.COLUMN_ORGINAL_LANGUAGE + " REAL NOT NULL, " +
                MovieContract.FavoriteMoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.FavoriteMoviesEntry.COLUMN_TRAILER + " TEXT," +
                MovieContract.FavoriteMoviesEntry.COLUMN_REVIEWS + " TEXT," +
                MovieContract.FavoriteMoviesEntry.COLUMN_BACKDROP_IMG+ " TEXT NOT NULL"+
                " );";

        final String SQL_CREATE_VIDEOS_MOVIES_TABLE = "CREATE TABLE " + MovieContract.VideosMovieEntry.TABLE_NAME + " (" +
                MovieContract.VideosMovieEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContract.VideosMovieEntry.COLUMN_VID+ " TEXT NOT NULL, " +
                MovieContract.VideosMovieEntry.COLUMN_MOVIE +" INTEGER NOT NULL, " +
                MovieContract.VideosMovieEntry.COLUMN_KEY + " TEXT NOT NULL, " +
                MovieContract.VideosMovieEntry.COLUMN_NAME + " TEXT NOT NULL " +
                " );";

        final String SQL_CREATE_REVIEWS_MOVIES_TABLE = "CREATE TABLE " + MovieContract.ReviewsMovieEntry.TABLE_NAME + " (" +
                MovieContract.ReviewsMovieEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContract.ReviewsMovieEntry.COLUMN_RID + " TEXT NOT NULL, " +
                MovieContract.ReviewsMovieEntry.COLUMN_MOVIE +" INTEGER NOT NULL, " +
                MovieContract.ReviewsMovieEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                MovieContract.ReviewsMovieEntry.COLUMN_CONTENT + " TEXT NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_FAVOTIRE_MOVIES_TABLE);
        db.execSQL(SQL_CREATE_VIDEOS_MOVIES_TABLE);
        db.execSQL(SQL_CREATE_REVIEWS_MOVIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.FavoriteMoviesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.VideosMovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.ReviewsMovieEntry.TABLE_NAME);

        onCreate(db);
    }
}
