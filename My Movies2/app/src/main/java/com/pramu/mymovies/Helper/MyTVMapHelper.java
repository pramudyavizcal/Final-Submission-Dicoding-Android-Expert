package com.pramu.mymovies.Helper;

import android.database.Cursor;

import com.pramu.mymovies.Database.DatabaseContract;
import com.pramu.mymovies.TV.MyTV;

import java.util.ArrayList;

public class MyTVMapHelper {
    public static ArrayList<MyTV> mapCursorToArrayList(Cursor TvShowItemsCursor) {
        ArrayList<MyTV> TvShowItems = new ArrayList<>();
        while (TvShowItemsCursor.moveToNext()) {
            int id = TvShowItemsCursor.getInt(TvShowItemsCursor.getColumnIndexOrThrow(DatabaseContract.MyTVColumns._ID));
            String title = TvShowItemsCursor.getString(TvShowItemsCursor.getColumnIndexOrThrow(DatabaseContract.MyTVColumns.TITLE_TV));
            String description = TvShowItemsCursor.getString(TvShowItemsCursor.getColumnIndexOrThrow(DatabaseContract.MyTVColumns.DESCRIPTION_TV));
            String image = TvShowItemsCursor.getString(TvShowItemsCursor.getColumnIndexOrThrow(DatabaseContract.MyTVColumns.PHOTO_TV));
            String release_date = TvShowItemsCursor.getString(TvShowItemsCursor.getColumnIndexOrThrow(DatabaseContract.MyTVColumns.RELEASE_DATE_TV));
            String rating = TvShowItemsCursor.getString(TvShowItemsCursor.getColumnIndexOrThrow(DatabaseContract.MyTVColumns.RATING_TV));
            TvShowItems.add(new MyTV(id, title, description, image, release_date, rating));
        }
        return TvShowItems;
    }
}
