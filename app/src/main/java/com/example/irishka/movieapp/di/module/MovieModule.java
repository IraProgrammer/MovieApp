package com.example.irishka.movieapp.di.module;

import com.example.irishka.movieapp.di.MovieScope;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.presenter.MoviesPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieModule {

    @Provides
    @MovieScope
    // TODO: здесь тоже конкретная реализация провайдится, можно убрать вообще
    // а @MoviesScope указать тогда над объявлением презентера
    static MoviesPresenter providePresenter(IMoviesRepository moviesRepository){
        return new MoviesPresenter(moviesRepository);
    }

}
