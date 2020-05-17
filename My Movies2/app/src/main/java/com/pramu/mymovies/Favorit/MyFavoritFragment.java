package com.pramu.mymovies.Favorit;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.pramu.mymovies.R;
import com.pramu.mymovies.SectionPageAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFavoritFragment extends Fragment {


    public MyFavoritFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_favorit, container, false);

        SectionPageAdapter sectionPageAdapter = new SectionPageAdapter( this, getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionPageAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);



        return view;}

}
