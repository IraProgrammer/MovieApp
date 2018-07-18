package com.example.irishka.movieapp.ui.view;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.irishka.movieapp.App;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.di.AppComponent;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.ui.presenter.MoviesPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Provides;

public class MoviesActivity extends MvpAppCompatActivity implements MoviesView {

    @BindView(R.id.movies_recycler_view)
    RecyclerView moviesRecyclerView;

    @Inject
    @InjectPresenter
    MoviesPresenter moviesPresenter;

    @ProvidePresenter
    MoviesPresenter providePresenter() {
        return moviesPresenter;
    }

    @Inject
    MoviesAdapter moviesAdapter;

    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.buildMovieComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);

        moviesRecyclerView.setLayoutManager(getLayoutManager());

        moviesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int lastVisibleItemPosition = getLastVisibleItemPosition();

                if (isLoading) return;
                if ((totalItemCount - visibleItemCount) <= (lastVisibleItemPosition + 20)
                        && lastVisibleItemPosition >= 0) {
                    isLoading = true;
                    moviesPresenter.downloadMovies();
                }
            }
        });

        moviesRecyclerView.setAdapter(moviesAdapter);

    }

    private int getColumns() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        int number = point.x;
        float scalefactor = getResources().getDisplayMetrics().density * 150;
        return (int) ((float) number / (float) scalefactor);
    }

    private StaggeredGridLayoutManager getLayoutManager() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(getColumns(), StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        return staggeredGridLayoutManager;
    }

    @Override
    public void showMovies(List<Movie> movies) {
        moviesAdapter.setMoviesList(movies);
    }

    @Override
    public void finishLoading() {
        isLoading = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        moviesPresenter.onStop();
    }

    private int getLastVisibleItemPosition() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) moviesRecyclerView.getLayoutManager();
        int[] into = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
        List<Integer> intoList = new ArrayList<>();
        for (int i : into) {
            intoList.add(i);
        }
        return Collections.max(intoList);
    }
}
