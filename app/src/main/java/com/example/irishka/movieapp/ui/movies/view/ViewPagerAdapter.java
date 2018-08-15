package com.example.irishka.movieapp.ui.movies.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.irishka.movieapp.ui.actor.films.view.FilmsFragment;
import com.example.irishka.movieapp.ui.actor.info.view.InfoFragment;
import com.example.irishka.movieapp.ui.movies.fragment.MainFilmsFragment;

import javax.inject.Inject;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private MoviesListActivity moviesListActivity;

    private MainFilmsFragment latest;

    private MainFilmsFragment nowPlaying;

    private MainFilmsFragment popular;

    private MainFilmsFragment topRated;

    private MainFilmsFragment upcoming;

    @Inject
    public ViewPagerAdapter(MoviesListActivity moviesListActivity) {
        super(moviesListActivity.getSupportFragmentManager());
        this.moviesListActivity = moviesListActivity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (latest == null) {
                    latest = MainFilmsFragment.newInstance();
                }
                return latest;
            case 1:
                if (nowPlaying == null) {
                    nowPlaying = MainFilmsFragment.newInstance();
                }
                return nowPlaying;
            case 2:
                if (popular == null) {
                    popular = MainFilmsFragment.newInstance();
                }
                return popular;
            case 3:
                if (topRated == null) {
                    topRated = MainFilmsFragment.newInstance();
                }
                return topRated;
            case 4:
                if (upcoming == null) {
                    upcoming = MainFilmsFragment.newInstance();
                }
                return upcoming;
            default:
                return null;
        }
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
