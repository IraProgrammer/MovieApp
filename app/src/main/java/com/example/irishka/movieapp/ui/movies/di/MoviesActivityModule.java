package com.example.irishka.movieapp.ui.movies.di;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.movies.presenter.MoviesPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class MoviesActivityModule {

    @Provides
    @PerActivity
    static MoviesPresenter providesMoviesPresenter(IMoviesRepository moviesRepository){
        return new MoviesPresenter(moviesRepository);
    }
}
