package com.pramu.mymovies.Favorit;

import com.pramu.mymovies.TV.MyTV;

import java.util.ArrayList;

interface LoadTV {
    void preExecute();
    void postExecute(ArrayList<MyTV> myTV);
}
