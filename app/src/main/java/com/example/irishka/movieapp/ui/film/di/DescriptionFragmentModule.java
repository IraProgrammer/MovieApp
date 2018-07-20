package com.example.irishka.movieapp.ui.film.di;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.film.presenter.DescriptionPresenter;
import com.example.irishka.movieapp.ui.film.view.DescriptionFragment;
import com.example.irishka.movieapp.ui.movies.presenter.MoviesListPresenter;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.example.irishka.movieapp.ui.movies.view.MoviesListActivity.MOVIE_ID;

@Module
public abstract class DescriptionFragmentModule {

    @Provides
    @PerFragment
    static DescriptionPresenter providesDescriptionPresenter(IMoviesRepository moviesRepository){
        return new DescriptionPresenter(moviesRepository);
    }

}
