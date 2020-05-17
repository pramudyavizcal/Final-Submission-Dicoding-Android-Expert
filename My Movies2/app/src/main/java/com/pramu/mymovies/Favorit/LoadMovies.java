package com.pramu.mymovies.Favorit;

import com.pramu.mymovies.Movies.MyMovies;

import java.util.ArrayList;

interface LoadMovies {
    void onResume();
    void preExecute();
    void postExecute(ArrayList<MyMovies> movies);
}
