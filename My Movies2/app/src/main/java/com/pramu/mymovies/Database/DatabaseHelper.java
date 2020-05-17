package com.pramu.mymovies.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.pramu.mymovies.Database.DatabaseContract.TABLE_MOVIES;
import static com.pramu.mymovies.Database.DatabaseContract.TABLE_TV;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "databasefavorit";
    private static final int DB_VERSION=1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static final String SQL_CREATE_TABLE_MOVIES = String.format("CREATE TABLE %s"
                    +"(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "%s TEXT NOT NULL UNIQUE,"+
                    "%s TEXT NOT NULL ,"+
                    "%s TEXT NOT NULL ,"+
                    "%s TEXT NOT NULL ,"+
                    "%s TEXT NOT NULL )",
            TABLE_MOVIES,
            DatabaseContract.MyMoviesColumns._ID,
            DatabaseContract.MyMoviesColumns.TITLE_MOVIES,
            DatabaseContract.MyMoviesColumns.DESCRIPTION_MOVIES,
            DatabaseContract.MyMoviesColumns.PHOTO_MOVIES,
            DatabaseContract.MyMoviesColumns.RELEASE_DATE_MOVIES,
            DatabaseContract.MyMoviesColumns.RATING_MOVIES
    );

    private static final String SQL_CREATE_TABLE_TV = String.format("CREATE TABLE %s"
                    +"(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "%s TEXT NOT NULL UNIQUE,"+
                    "%s TEXT NOT NULL ,"+
                    "%s TEXT NOT NULL ,"+
                    "%s TEXT NOT NULL ,"+
                    "%s TEXT NOT NULL )",
            TABLE_TV,
            DatabaseContract.MyTVColumns._ID,
            DatabaseContract.MyTVColumns.TITLE_TV,
            DatabaseContract.MyTVColumns.DESCRIPTION_TV,
            DatabaseContract.MyTVColumns.PHOTO_TV,
            DatabaseContract.MyTVColumns.RELEASE_DATE_TV,
            DatabaseContract.MyTVColumns.RATING_TV
    );

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIES);
        db.execSQL(SQL_CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TV);

        onCreate(db);
    }
}
