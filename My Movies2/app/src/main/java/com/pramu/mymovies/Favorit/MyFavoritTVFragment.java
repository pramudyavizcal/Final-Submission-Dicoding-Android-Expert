package com.pramu.mymovies.Favorit;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.pramu.mymovies.Helper.MyTVHelper;
import com.pramu.mymovies.Helper.MyTVMapHelper;
import com.pramu.mymovies.Movies.MyMovies;
import com.pramu.mymovies.Movies.MyMoviesDetail;
import com.pramu.mymovies.R;
import com.pramu.mymovies.TV.MyTV;
import com.pramu.mymovies.TV.MyTVAdapter;
import com.pramu.mymovies.TV.MyTVDetail;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFavoritTVFragment extends Fragment implements LoadTV {
    private MyTVAdapter adapter;
    private ProgressBar progressBarTvShowFavorit;
    private MyTVHelper tvHelper;


    private ArrayList<MyTV> items = new ArrayList<>();

    private RecyclerView rvTVFav;

    public MyFavoritTVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_favorit_tv, container, false);
        rvTVFav = view.findViewById(R.id.rv_TV_favorit);
        rvTVFav.setHasFixedSize(true);
        progressBarTvShowFavorit = view.findViewById(R.id.progressBarFavoritTV);
        tvHelper = MyTVHelper.getInstance(getContext());
        tvHelper.open();
        showRecyclerView();
        new MyFavoritTVFragment.LoadTvAsync(tvHelper,this).execute();
        adapter.setOnItemClickCallback(new MyTVAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MyTV data) {
                SelectedTvShow(data);
            }
        });
        return view;
    }

    private void showRecyclerView() {
        adapter = new MyTVAdapter();
        adapter.notifyDataSetChanged();
        rvTVFav.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTVFav.setAdapter(adapter);
    }

    @Override
    public void preExecute() {
        progressBarTvShowFavorit.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<MyTV> myTV) {
        progressBarTvShowFavorit.setVisibility(View.INVISIBLE);
        adapter.setData(myTV);
        items.addAll(myTV);
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<MyTV>> {

        private final WeakReference<MyTVHelper> myTVHelperWeakReference;
        private final WeakReference<LoadTV> loadTVWeakReference;

        private LoadTvAsync(MyTVHelper myTVHelper, LoadTV loadTV) {
            myTVHelperWeakReference = new WeakReference<>(myTVHelper);
            loadTVWeakReference = new WeakReference<>(loadTV);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadTVWeakReference.get().preExecute();
        }

        @Override
        protected ArrayList<MyTV> doInBackground(Void... voids) {
            Cursor dataCursor = myTVHelperWeakReference.get().queryAll();
            return MyTVMapHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<MyTV> myTVS) {
            super.onPostExecute(myTVS);
            loadTVWeakReference.get().postExecute(myTVS);
        }
    }
    private void SelectedTvShow(MyTV tvShow) {
        Intent intent = new Intent(Objects.requireNonNull(getActivity()).getApplication(), MyTVDetail.class);
        intent.putExtra(MyTVDetail.EXTRA_TV, tvShow);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvHelper.close();
    }
}