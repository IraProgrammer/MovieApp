package com.example.irishka.movieapp.ui.movie.di;

import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.movie.creators.presenter.CreatorsPresenter;
import com.example.irishka.movieapp.ui.movie.creators.view.ActorsAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class CreatorsFragmentModule {

    @Provides
    @PerFragment
    static CreatorsPresenter providesCreatorsPresenter(IMoviesRepository moviesRepository, long movieId) {
        return new CreatorsPresenter(moviesRepository, movieId);
    }

    @Provides
    @PerFragment
    static ActorsAdapter providesActorsAdapter(){
        return new ActorsAdapter();
    }

}