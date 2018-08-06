package com.example.irishka.movieapp.ui.movie.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.ui.movie.creators.view.CreatorsFragment;
import com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private MovieActivity movieActivity;

    @Inject
    public ViewPagerAdapter(MovieActivity movieActivity) {
        super(movieActivity.getSupportFragmentManager());
        this.movieActivity = movieActivity;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return DescriptionFragment.newInstance();
            case 1:
                return CreatorsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return movieActivity.getString(R.string.description_fragment);
            case 1:
                return movieActivity.getString(R.string.creators_fragment);
            default:
                return "";
        }
    }
}
