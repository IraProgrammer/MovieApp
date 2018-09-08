package com.example.irishka.movieapp.ui.movies.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageButton;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.MainType;
import com.example.irishka.movieapp.ui.filters.view.FiltersActivity;
import com.example.irishka.movieapp.ui.movies.fragment.view.MainFilmsFragment;
import com.example.irishka.movieapp.ui.search.view.SearchActivity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class MoviesListActivity extends DaggerAppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.main_pager)
    ViewPager viewPager;

    @BindView(R.id.btn_search)
    ImageButton btnSearch;

    @BindView(R.id.btn_filters)
    ImageButton btnFilters;

    @BindView(R.id.root)
    CoordinatorLayout root;

    @Inject
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppCompatDarkNoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        ButterKnife.bind(this);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        btnSearch.setOnClickListener(view -> {
            Intent intent = new Intent(MoviesListActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        btnFilters.setOnClickListener(view -> {
            Intent intent = new Intent(MoviesListActivity.this, FiltersActivity.class);
            startActivity(intent);
        });
    }
}
