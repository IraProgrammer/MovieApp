package com.example.irishka.movieapp.ui.movie.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Description;
import com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;

import static com.example.irishka.movieapp.ui.movies.view.MoviesListActivity.MOVIE_ID;

public class MovieActivity extends DaggerAppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @Inject
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        ButterKnife.bind(this);

//        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance();
//        FragmentManager fm = getSupportFragmentManager();
//        fm.beginTransaction()
//                .add(R.id.container222, descriptionFragment)
//                .commit();

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        DescriptionFragment desc = (DescriptionFragment) getSupportFragmentManager().findFragmentById(R.id.container222);
//        Toast.makeText(this, desc.getTitle(), Toast.LENGTH_LONG).show();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tabs, menu);
        return true;
    }
}
