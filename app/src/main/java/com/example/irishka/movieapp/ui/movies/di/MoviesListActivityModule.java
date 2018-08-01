package com.example.irishka.movieapp.ui.movies.di;

import android.graphics.Point;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.movies.presenter.MoviesListPresenter;
import com.example.irishka.movieapp.ui.movies.view.MoviesListActivity;
import com.example.irishka.movieapp.ui.movies.view.MoviesListAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class MoviesListActivityModule {

    @Provides
    @PerActivity
    static MoviesListAdapter providesMoviesAdapter(MoviesListActivity moviesActivity){
        return new MoviesListAdapter(moviesActivity);
    }

    @Provides
    @PerActivity
    static StaggeredGridLayoutManager providesStaggeredGridLayoutManager(MoviesListActivity moviesActivity){

            Point point = new Point();
            moviesActivity.getWindowManager().getDefaultDisplay().getSize(point);
            int number = point.x;
            float scalefactor = moviesActivity.getResources().getDisplayMetrics().density * 150;
            int columns = (int) ((float) number / (float) scalefactor);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        return staggeredGridLayoutManager;
    }
}
