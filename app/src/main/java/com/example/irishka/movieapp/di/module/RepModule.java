package com.example.irishka.movieapp.di.module;

import com.example.irishka.movieapp.data.database.MovieDao;
import com.example.irishka.movieapp.data.mapper.MoviesMapper;
import com.example.irishka.movieapp.data.network.MoviesApi;
import com.example.irishka.movieapp.data.repository.MoviesRepository;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.presenter.MoviesPresenter;
import com.example.irishka.movieapp.ui.view.MoviesActivity;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Module
public abstract class RepModule {

    @Binds
    @Singleton
    abstract IMoviesRepository provideRepository(MoviesRepository moviesRepository);

}
