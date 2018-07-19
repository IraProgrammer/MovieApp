package com.example.irishka.movieapp.di.module;

import com.example.irishka.movieapp.data.repository.MoviesRepository;
import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.movies.presenter.MoviesPresenter;
import com.example.irishka.movieapp.ui.movies.view.MoviesAdapter;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepModule {

    @Binds
    @Singleton
    abstract IMoviesRepository provideRepository(MoviesRepository moviesRepository);

  /*  @Provides
    static MoviesAdapter providesMoviesAdapter(MoviesAdapter.OnItemClickListener onItemClickListener){
        return new MoviesAdapter(onItemClickListener);
    } */

}
