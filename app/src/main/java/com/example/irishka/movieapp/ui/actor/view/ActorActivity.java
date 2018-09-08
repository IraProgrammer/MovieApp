package com.example.irishka.movieapp.ui.actor.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irishka.movieapp.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

import static com.example.irishka.movieapp.ui.movie.creators.view.CreatorsFragment.ACTOR;

public class ActorActivity extends DaggerAppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.main_pager)
    ViewPager viewPager;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.btn_home)
    ImageView btnHome;

    @Inject
    ActorViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        ButterKnife.bind(this);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        toolbarTitle.setText(getIntent().getStringExtra(ACTOR));
        btnHome.setOnClickListener(view -> finish());
    }
}