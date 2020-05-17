package com.pramu.mymovies.TV;


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

import com.pramu.mymovies.MyCustomClick;
import com.pramu.mymovies.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyTVFragment extends Fragment implements View.OnClickListener{

    private MyTVAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView rvMyTV;
    private ModelTV modelTV;

    public MyTVFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_tv,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MyTVAdapter();
        adapter.notifyDataSetChanged();

        rvMyTV = view.findViewById(R.id.rv_TV);
        rvMyTV.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMyTV.setAdapter(adapter);
        rvMyTV.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.bringToFront();

        modelTV = ViewModelProviders.of(getActivity()).get(ModelTV.class);
        modelTV.getTV().observe(getActivity(), getTvShow);

        adapter.setOnItemClickCallback(new MyTVAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MyTV data) {
                SelectedTvShow(data);
            }
        });

    }
    private void SelectedTvShow(MyTV tvShow) {
        Intent intent = new Intent(getActivity().getApplication(), MyTVDetail.class);
        intent.putExtra(MyTVDetail.EXTRA_TV, tvShow);
        startActivity(intent);
    }
    private Observer<ArrayList<MyTV>> getTvShow = new Observer<ArrayList<MyTV>>() {
        @Override
        public void onChanged(@Nullable ArrayList<MyTV> tvShows) {
            if (tvShows != null) {
                adapter.setData(tvShows);
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstaceState) {
        super.onActivityCreated(savedInstaceState);

        modelTV.setTv();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        modelTV.getTV().removeObserver(getTvShow);
    }

    @Override
    public void onClick(View v) {

    }

}
