package com.pramu.mymovies.Helper;

import android.database.Cursor;

import com.pramu.mymovies.Database.DatabaseContract;
import com.pramu.mymovies.Movies.MyMovies;

import java.util.ArrayList;

public class MyMovieMapHelper {
    public static ArrayList<MyMovies> mapCursorToArrayList(Cursor MoviesItemsCursor) {
        ArrayList<MyMovies> MoviesItems = new ArrayList<>();
        while (MoviesItemsCursor.moveToNext()) {
            int id = MoviesItemsCursor.getInt(MoviesItemsCursor.getColumnIndexOrThrow(DatabaseContract.MyMoviesColumns._ID));
            String title = MoviesItemsCursor.getString(MoviesItemsCursor.getColumnIndexOrThrow(DatabaseContract.MyMoviesColumns.TITLE_MOVIES));
            String description = MoviesItemsCursor.getString(MoviesItemsCursor.getColumnIndexOrThrow(DatabaseContract.MyMoviesColumns.DESCRIPTION_MOVIES));
            String image = MoviesItemsCursor.getString(MoviesItemsCursor.getColumnIndexOrThrow(DatabaseContract.MyMoviesColumns.PHOTO_MOVIES));
            String release_date = MoviesItemsCursor.getString(MoviesItemsCursor.getColumnIndexOrThrow(DatabaseContract.MyMoviesColumns.RELEASE_DATE_MOVIES));
            String rating = MoviesItemsCursor.getString(MoviesItemsCursor.getColumnIndexOrThrow(DatabaseContract.MyMoviesColumns.RATING_MOVIES));
            MoviesItems.add(new MyMovies(id, title, description,image, release_date, rating));
        }
        return MoviesItems;
    }
}
