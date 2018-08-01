package com.example.irishka.movieapp.ui.movie.creators.actor.di;

import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.movie.creators.actor.info.InfoFragment;
import com.example.irishka.movieapp.ui.movie.creators.actor.info.InfoPresenter;
import com.example.irishka.movieapp.ui.movie.creators.actor.info.PhotosAdapter;
import com.example.irishka.movieapp.ui.movie.creators.view.ActorsAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class InfoFragmentModule {

    @Provides
    @PerFragment
    static InfoPresenter providesInfoPresenter(IMoviesRepository moviesRepository, long movieId) {
        return new InfoPresenter(moviesRepository, movieId);
    }
}
