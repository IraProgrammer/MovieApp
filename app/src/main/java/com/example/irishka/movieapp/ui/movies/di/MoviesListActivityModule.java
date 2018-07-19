package com.example.irishka.movieapp.ui.movies.di;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.film.di.DescriptionFragmentModule;
import com.example.irishka.movieapp.ui.film.view.DescriptionFragment;
import com.example.irishka.movieapp.ui.movies.presenter.MoviesListPresenter;
import com.example.irishka.movieapp.ui.movies.view.MoviesListActivity;
import com.example.irishka.movieapp.ui.movies.view.MoviesListAdapter;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;


@Module
public class MoviesListActivityModule {

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
