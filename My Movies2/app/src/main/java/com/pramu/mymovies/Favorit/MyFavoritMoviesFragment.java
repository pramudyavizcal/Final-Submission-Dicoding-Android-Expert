package com.pramu.mymovies.Favorit;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.pramu.mymovies.Helper.MyMovieMapHelper;
import com.pramu.mymovies.Helper.MyMoviesHelper;
import com.pramu.mymovies.Movies.MyMovies;
import com.pramu.mymovies.Movies.MyMoviesAdapter;
import com.pramu.mymovies.Movies.MyMoviesDetail;
import com.pramu.mymovies.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFavoritMoviesFragment extends Fragment implements LoadMovies{
    private MyMoviesAdapter adapter;
    private ProgressBar progressBarMoviesFavorit;
    private MyMoviesHelper movieHelper;

    private ArrayList<MyMovies> items = new ArrayList<>();

    private RecyclerView rvMoviesFav;

    public MyFavoritMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_favorit_movies, container, false);
        rvMoviesFav = view.findViewById(R.id.rv_movies_favorit);
        rvMoviesFav.setHasFixedSize(true);
        progressBarMoviesFavorit = view.findViewById(R.id.progressBarFavoritMovies);
        movieHelper = MyMoviesHelper.getInstance(getContext());
        movieHelper.open();
        showRecyclerView();
        new MyFavoritMoviesFragment.LoadMoviesAsync(movieHelper,this).execute();
        adapter.setOnItemClickCallback(new MyMoviesAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MyMovies data) {
                SelectMovies(data);
            }
        });
        return view;
    }


    private void showRecyclerView() {
        adapter = new MyMoviesAdapter();
        adapter.notifyDataSetChanged();
        rvMoviesFav.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMoviesFav.setAdapter(adapter);
    }

    @Override
    public void preExecute() {
        progressBarMoviesFavorit.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<MyMovies> movies) {
progressBarMoviesFavorit.setVisibility(View.INVISIBLE);
adapter.setData(movies);
items.addAll(movies);
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadMoviesAsync extends AsyncTask<Void,Void,ArrayList<MyMovies>>{
    private final WeakReference<MyMoviesHelper> myMoviesHelperWeakReference;
    private final WeakReference<LoadMovies> loadMoviesWeakReference;

    private LoadMoviesAsync(MyMoviesHelper myMoviesHelper, LoadMovies loadMovies){
        myMoviesHelperWeakReference = new WeakReference<>(myMoviesHelper);
        loadMoviesWeakReference = new WeakReference<>(loadMovies);
    }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadMoviesWeakReference.get().preExecute();
        }

        @Override
        protected ArrayList<MyMovies> doInBackground(Void... voids) {
        Cursor dataCursor = myMoviesHelperWeakReference.get().queryAll();
        return MyMovieMapHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<MyMovies> myMovies) {
            super.onPostExecute(myMovies);
            loadMoviesWeakReference.get().postExecute(myMovies);
        }
    }
    private void SelectMovies(MyMovies movies) {
        Intent intent = new Intent(Objects.requireNonNull(getActivity()).getApplication(), MyMoviesDetail.class);
        intent.putExtra(MyMoviesDetail.EXTRA_MOVIE, movies);
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }
}
