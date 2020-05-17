package com.pramu.mymovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pramu.mymovies.Favorit.MyFavoritFragment;
import com.pramu.mymovies.Favorit.MyFavoritMoviesFragment;
import com.pramu.mymovies.Favorit.MyFavoritTVFragment;

public class SectionPageAdapter extends FragmentPagerAdapter {

    private final MyFavoritFragment myFavoritFragment;

    public SectionPageAdapter(@NonNull MyFavoritFragment context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        myFavoritFragment = context;
    }

    @StringRes
    private final int[] TAB_FRAGMENT = new int[]{
            R.string.navigationMovies,
            R.string.navigationTV
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MyFavoritMoviesFragment();
                break;

            case 1:
                fragment = new MyFavoritTVFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return myFavoritFragment.getResources().getString(TAB_FRAGMENT[position]);
    }
}
