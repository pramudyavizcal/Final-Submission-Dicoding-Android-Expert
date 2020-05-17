package com.pramu.mymovies.Movies;


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.pramu.mymovies.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyMoviesFragment extends Fragment implements SearchView.OnClickListener{
    private MyMoviesAdapter adapter;
    private ArrayList<MyMovies> listMovies;
    private ProgressBar progressBar;
    private RecyclerView rvMymovies;
    private String lg = Locale.getDefault().toLanguageTag();
    private ModelMovies modelMovies;
    private SearchView searchView;
    private static final String API_KEY = "b67c000b9ab21b8a8cd1ee976dcc11af";

    public MyMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter =new MyMoviesAdapter();
        adapter.notifyDataSetChanged();
        rvMymovies = view.findViewById(R.id.rv_movies);
        rvMymovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMymovies.setAdapter(adapter);
        rvMymovies.setHasFixedSize(true);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.bringToFront();
        modelMovies = ViewModelProviders.of(getActivity()).get(ModelMovies.class);
        modelMovies.getMovie().observe(getActivity(),getMovies);
        searchView = (SearchView) view.findViewById(R.id.searchMovie);
        adapter.setOnItemClickCallback(new MyMoviesAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MyMovies data) {
                SelectMovies(data);
            }
        });
    }

    private Observer<ArrayList<MyMovies>> getMovies = new Observer<ArrayList<MyMovies>>() {
        @Override
        public void onChanged(@Nullable ArrayList<MyMovies> movies) {
            if (movies != null) {
                adapter.setData(movies);
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        modelMovies.setMovie();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        modelMovies.getMovie().removeObserver(getMovies);
    }
   private void SelectMovies(MyMovies movies) {
        Intent intent = new Intent(Objects.requireNonNull(getActivity()).getApplication(), MyMoviesDetail.class);
        intent.putExtra(MyMoviesDetail.EXTRA_MOVIE, movies);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
    }
}
