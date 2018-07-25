package com.example.irishka.movieapp.di.module;

import com.example.irishka.movieapp.data.mappers.GenreMapper;
import com.example.irishka.movieapp.data.mappers.ProductionCountryMapper;
import com.example.irishka.movieapp.data.repository.MoviesRepository;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepModule {

    @Binds
    @Singleton
    abstract IMoviesRepository provideRepository(MoviesRepository moviesRepository);

//    @Provides
//    static GenreMapper provideGenresMapper(){
//        return new GenreMapper();
//    }
//
//    @Provides
//    static ProductionCountryMapper provideProductionCountryMapper(){
//        return new ProductionCountryMapper();
//    }

}
