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

    // TODO: ненужно, MoviesListPresenter не покрыт интерфейсом и конструктор имеет аннотацию @Inject
    // он и так заинжектится
    @Provides
    @PerActivity
    static MoviesListPresenter providesMoviesPresenter(IMoviesRepository moviesRepository){
        return new MoviesListPresenter(moviesRepository);
    }

    @Provides
    @PerActivity
    static MoviesListAdapter providesMoviesAdapter(MoviesListActivity moviesActivity){
        return new MoviesListAdapter(moviesActivity);
    }
}
