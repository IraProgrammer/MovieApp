package com.example.irishka.movieapp.ui.movie.creators.actor.di;

import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.movie.creators.actor.films.FilmsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class FilmsFragmentModule {

    @Provides
    @PerFragment
    static FilmsPresenter providesFilmsPresenter(IMoviesRepository moviesRepository, long movieId) {
        return new FilmsPresenter(moviesRepository, movieId);
    }

}
