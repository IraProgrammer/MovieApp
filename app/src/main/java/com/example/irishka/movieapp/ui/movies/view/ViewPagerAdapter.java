package com.example.irishka.movieapp.ui.movies.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.ui.actor.films.view.FilmsFragment;
import com.example.irishka.movieapp.ui.actor.info.view.InfoFragment;
import com.example.irishka.movieapp.ui.movies.fragment.MainFilmsFragment;

import javax.inject.Inject;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public enum Tabs {
        NOW_PLAYING("now_playing"),
        POPULAR("popular"),
        TOP_RATED("top_rated"),
        UPCOMING("upcoming");

        private String title;

        private Tabs(String title) {
            this.title = title;
        }

    }

    private MoviesListActivity moviesListActivity;

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
                if (nowPlaying == null) {
                    nowPlaying = MainFilmsFragment.newInstance(Tabs.NOW_PLAYING);
                }
                return nowPlaying;
            case 1:
                if (popular == null) {
                    popular = MainFilmsFragment.newInstance(Tabs.POPULAR);
                }
                return popular;
            case 2:
                if (topRated == null) {
                    topRated = MainFilmsFragment.newInstance(Tabs.TOP_RATED);
                }
                return topRated;
            case 3:
                if (upcoming == null) {
                    upcoming = MainFilmsFragment.newInstance(Tabs.UPCOMING);
                }
                return upcoming;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return moviesListActivity.getString(R.string.now_playing_fragment);
            case 1:
                return moviesListActivity.getString(R.string.popular_fragment);
            case 2:
                return moviesListActivity.getString(R.string.top_rated_fragment);
            case 3:
                return moviesListActivity.getString(R.string.upcoming_fragment);
            default:
                return "";
        }
    }
}
