package com.example.irishka.movieapp.ui.film.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    private List<String> titles = new ArrayList<>();

   // @Inject
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        for (Fragment f: fragments) {
            titles.add(f.toString());
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
