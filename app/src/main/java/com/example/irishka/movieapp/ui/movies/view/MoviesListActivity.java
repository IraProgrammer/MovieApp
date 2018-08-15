package com.example.irishka.movieapp.ui.movies.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.ui.search.view.SearchActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.DaggerAppCompatActivity;

public class MoviesListActivity extends DaggerAppCompatActivity{

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.main_pager)
    ViewPager viewPager;

    @BindView(R.id.btn_search)
    ImageButton btnSearch;

    @Inject
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        ButterKnife.bind(this);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoviesListActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
