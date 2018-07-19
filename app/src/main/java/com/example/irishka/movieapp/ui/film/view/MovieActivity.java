package com.example.irishka.movieapp.ui.film.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.irishka.movieapp.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class MovieActivity extends DaggerAppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @Inject
    DescriptionFragment descriptionFragment;

    @Inject
    CreatorsFragment creatorsFragment;

    @Inject
    TrailersFragment trailersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        ButterKnife.bind(this);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(descriptionFragment, "FRAG1");
        adapter.addFragment(trailersFragment, "FRAG2");
        adapter.addFragment(creatorsFragment, "FRAG3");

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }
}
