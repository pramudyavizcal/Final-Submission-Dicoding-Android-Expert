package com.pramu.mymovies.Helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.pramu.mymovies.Database.DatabaseHelper;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.pramu.mymovies.Database.DatabaseContract.MyMoviesColumns.TITLE_MOVIES;
import static com.pramu.mymovies.Database.DatabaseContract.TABLE_MOVIES;

public class MyMoviesHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIES;
    private static DatabaseHelper dataBaseHelper;
    private static MyMoviesHelper INSTANCE;
    private static SQLiteDatabase database;

    private MyMoviesHelper(Context context){
        dataBaseHelper = new DatabaseHelper(context);
    }
    public static MyMoviesHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyMoviesHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }



    public Boolean getOne(String name){
        String querySingleRecord = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + TITLE_MOVIES+ " " + " LIKE " +"'"+name+"'" ;
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(querySingleRecord,null);
        cursor.moveToFirst();
        Log.d("cursor", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0 ){

            return true;
        }else if(cursor.getCount() == 0){
            return false;
        }
        return false;
    }


    public static long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteByTitle(String title) {
        return database.delete(DATABASE_TABLE, TITLE_MOVIES + " = ?", new String[]{title});
    }
}