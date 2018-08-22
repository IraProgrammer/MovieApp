package com.example.irishka.movieapp.ui.movies.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.Tabs;
import com.example.irishka.movieapp.ui.movies.fragment.view.MainFilmsFragment;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private MoviesListActivity moviesListActivity;

    private SparseArray<Fragment> map = new SparseArray<Fragment>();

    @Inject
    public ViewPagerAdapter(MoviesListActivity moviesListActivity) {
        super(moviesListActivity.getSupportFragmentManager());
        this.moviesListActivity = moviesListActivity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (map.get(0) == null) {
                    map.put(0, MainFilmsFragment.newInstance(Tabs.NOW_PLAYING));
                }
                return map.get(0);
            case 1:
                if (map.get(1) == null) {
                    map.put(1, MainFilmsFragment.newInstance(Tabs.POPULAR));
                }
                return map.get(1);
            case 2:
                if (map.get(2) == null) {
                    map.put(2, MainFilmsFragment.newInstance(Tabs.TOP_RATED));
                }
                return map.get(2);
            case 3:
                if (map.get(3) == null) {
                    map.put(3, MainFilmsFragment.newInstance(Tabs.UPCOMING));
                }
                return map.get(3);
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
