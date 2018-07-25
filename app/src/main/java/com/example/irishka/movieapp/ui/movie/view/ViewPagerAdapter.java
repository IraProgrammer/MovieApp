package com.example.irishka.movieapp.ui.movie.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.irishka.movieapp.ui.movie.creators.view.CreatorsFragment;
import com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment;
import com.example.irishka.movieapp.ui.movie.review.ReviewFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<MovieFragment> fragments = new ArrayList<>();

    private List<String> titles = new ArrayList<>();

    @Inject
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        setFragments();
    }

    @Override
    public MovieFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    private void setFragments() {
        fragments.add(DescriptionFragment.newInstance());
        fragments.add(CreatorsFragment.newInstance());
        fragments.add(ReviewFragment.newInstance());

        for (MovieFragment f: fragments) {
            titles.add(f.getTitle());
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
