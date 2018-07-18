package com.example.irishka.movieapp.di.module;

import com.example.irishka.movieapp.data.database.MovieDao;
import com.example.irishka.movieapp.data.mapper.MoviesMapper;
import com.example.irishka.movieapp.data.network.MoviesApi;
import com.example.irishka.movieapp.data.repository.MoviesRepository;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepModule {

    @Provides
    static MoviesMapper provideMapper(){
        return new MoviesMapper();
    }

    @Provides
    @Singleton
    static IMoviesRepository provideRepository(MoviesMapper moviesMapper, MovieDao movieDao, MoviesApi moviesApi){
        return new MoviesRepository(moviesMapper, movieDao, moviesApi);
    }
}
