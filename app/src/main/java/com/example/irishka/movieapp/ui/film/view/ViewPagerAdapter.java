package com.example.irishka.movieapp.ui.film.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    private List<String> titles = new ArrayList<>();

    @Inject
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        setFragments();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    private void setFragments() {
        fragments.add(DescriptionFragment.newInstance());
        fragments.add(TrailersFragment.newInstance());
        fragments.add(CreatorsFragment.newInstance());

        for (Fragment f: fragments) {
            titles.add(f.toString());
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
