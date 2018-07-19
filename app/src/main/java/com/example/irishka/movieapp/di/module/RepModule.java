package com.example.irishka.movieapp.di.module;

import com.example.irishka.movieapp.data.repository.MoviesRepository;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepModule {

    @Binds
    @Singleton
    abstract IMoviesRepository provideRepository(MoviesRepository moviesRepository);

}
