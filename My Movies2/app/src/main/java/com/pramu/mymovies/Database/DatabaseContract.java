package com.pramu.mymovies.Database;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_MOVIES = "movies";
    public static final class MyMoviesColumns implements BaseColumns{
        public static String TITLE_MOVIES = "title_movies";
        public static String DESCRIPTION_MOVIES = "overview_movies";
        public static String PHOTO_MOVIES = "image_movies";
        public static String RELEASE_DATE_MOVIES = "releasedate_movies";
        public static String RATING_MOVIES = "rating_movies";
    }

    public static String TABLE_TV = "tv";
    public static final class MyTVColumns implements BaseColumns{
        public static String TITLE_TV = "title_tv";
        public static String DESCRIPTION_TV = "overview_tv";
        public static String PHOTO_TV = "image_tv";
        public static String RELEASE_DATE_TV = "releasedate_tv";
        public static String RATING_TV = "rating_tv";
    }
}
