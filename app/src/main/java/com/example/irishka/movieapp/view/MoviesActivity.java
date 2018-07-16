package com.example.irishka.movieapp.view;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.model.Pojo.ConcreteMovie;
import com.example.irishka.movieapp.presenter.MoviesPresenter;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;

public class MoviesActivity extends MvpAppCompatActivity implements MoviesView {

    @BindView(R.id.movies_recycler_view)
    RecyclerView moviesRecyclerView;

    @InjectPresenter
    MoviesPresenter moviesPresenter;

    MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);

        float scalefactor = getResources().getDisplayMetrics().density * 150;
        int number = getWindowManager().getDefaultDisplay().getWidth();
        int columns = (int) ((float) number / (float) scalefactor);

        //moviesRecyclerView.setLayoutManager(new GridLayoutManager(this, columns));

        //moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

     //   moviesRecyclerView.setLayoutManager(new FlexboxLayoutManager(this));

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        moviesRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        //   moviesRecyclerView.setItemAnimator(null);

        moviesAdapter = new MoviesAdapter();
        moviesRecyclerView.setAdapter(moviesAdapter);

    }

    @Override
    public void showMovies(List<ConcreteMovie> movies) {
        moviesAdapter.setMoviesList(movies);
    }

    @Override
    protected void onStop() {
        super.onStop();
        moviesPresenter.onStop();
    }
}
