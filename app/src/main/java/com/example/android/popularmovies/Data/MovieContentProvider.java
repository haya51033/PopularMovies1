package com.example.android.popularmovies.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.content.ContentValues.TAG;
import static com.example.android.popularmovies.Data.MovieContract.FavoriteMoviesEntry.TABLE_NAME;

public class MovieContentProvider extends ContentProvider {

    public static final int MOVIES = 1;
    public static final int MOVIE_WITH_ID = 2;

    public static final int VIDEOS = 3;
    public static final int VIDEO_WITH_ID = 4;

    public static final int REVIEWS = 5;
    public static final int REVIEW_WITH_ID = 6;

    private static final UriMatcher sUrimatcher = buildUriMatcher();
    private MoviesDB dbHelper;



    public static UriMatcher buildUriMatcher(){

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE, MOVIES);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE + "/#", MOVIE_WITH_ID);

        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_VIDEO, VIDEOS);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_VIDEO + "/#", VIDEO_WITH_ID);

        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_REVIEW, REVIEWS);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_REVIEW + "/*", REVIEW_WITH_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new MoviesDB(context);

        return true;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        Log.i(TAG, "bulkInsert");
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int mach = sUrimatcher.match(uri);
        if (values.length == 0)
            return 0;
        int insertCount = 0;
        try {
            switch (mach) {

                case VIDEOS:
                    try {
                        db.beginTransaction();
                        for (ContentValues value : values) {
                            long id = db.insertWithOnConflict(MovieContract.VideosMovieEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                            if (id > 0)
                                insertCount++;
                        }
                        db.setTransactionSuccessful();
                    } catch (Exception e) {
                        //  error handling
                    } finally {
                        db.endTransaction();
                    }
                    break;
                case REVIEWS:
                    try {
                        db.beginTransaction();
                        for (ContentValues value : values) {
                            long id = db.insertWithOnConflict(MovieContract.ReviewsMovieEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                            if (id > 0)
                                insertCount++;
                        }
                        db.setTransactionSuccessful();
                    } catch (Exception e) {
                        //  error handling
                    } finally {
                        db.endTransaction();
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }
            getContext().getContentResolver().notifyChange(uri, null);
        } catch (Exception e) {
            Log.i(TAG, "Exception : " + e);
        }
        return insertCount;

    }
        @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int mach = sUrimatcher.match(uri);
        Uri returnUri;

        switch (mach)
        {
            case MOVIES:
            {
                long id = db.insert(TABLE_NAME,null,contentValues);
                if(id > 0){
                    returnUri = ContentUris.withAppendedId(MovieContract.FavoriteMoviesEntry.CONTENT_URI, id);

                }
                else {
                    throw new android.database.SQLException("Failed to insert row "+ uri);
                }
                break;
            }
                default:
                    throw new UnsupportedOperationException("Unknown URI"+ uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
  /*  private Cursor getMovieId(Uri uri, String[] projection, String sortOrder) {
        String movieId = MovieContract.FavoriteMoviesEntry.g
        String popularid = DataContract.Popular.getPopularIdfromUri(uri);
        String[] selectionArgs;
        String selection;
        selection = PopularSelection;
        selectionArgs = new String[]{popularid};
        return moviesDB.getReadableDatabase().query(DataContract.Popular.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }*/

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings,
                        @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final int mach = sUrimatcher.match(uri);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor returnCursor = null;
        switch (mach) {
            case MOVIES: {
                returnCursor = db.query(MovieContract.FavoriteMoviesEntry.TABLE_NAME,
                        strings, s, strings1, null, null, s1);
                Log.i(TAG, returnCursor.toString()+"mmmmmmmmmmmmmmmmmmmmmmm");
                break;
            }
            case MOVIE_WITH_ID: {
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String mSelectionArgs [] =new String[]{id};
                returnCursor = db.query(
                        MovieContract.FavoriteMoviesEntry.TABLE_NAME,
                        strings,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        s1);

                break;
            }
            case VIDEOS: {
                returnCursor = db.query(MovieContract.VideosMovieEntry.TABLE_NAME,
                        strings, s, strings1, null, null, s1);
                break;
            }
            case VIDEO_WITH_ID: {
               // returnCursor = getFavourId(uri, projection, sortOrder);
                break;
            }
            case REVIEWS: {
                returnCursor = db.query(MovieContract.ReviewsMovieEntry.TABLE_NAME,
                        strings, s, strings1, null, null, s1);
                break;
            }
            case REVIEW_WITH_ID: {
              //  returnCursor = getRatedID(uri, projection, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;    }
}
