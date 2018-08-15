package com.example.irishka.movieapp.ui.movies.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.irishka.movieapp.ui.movies.fragment.MainFilmsFragment;

import javax.inject.Inject;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private MoviesListActivity moviesListActivity;

    @Inject
    public ViewPagerAdapter(MoviesListActivity moviesListActivity) {
        super(moviesListActivity.getSupportFragmentManager());
        this.moviesListActivity = moviesListActivity;
    }

    @Override
    public Fragment getItem(int position) {

        return MainFilmsFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "1";
            case 1:
                return "2";
            case 2:
                return "3";
            case 3:
                return "4";
            case 4:
                return "5";
            default:
                return "";
        }
    }
}
