package com.example.irishka.movieapp.ui.movie.di;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.movie.creators.presenter.CreatorsPresenter;
import com.example.irishka.movieapp.ui.movie.creators.view.ActorsAdapter;
import com.example.irishka.movieapp.ui.movie.description.presenter.DescriptionPresenter;
import com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment;
import com.example.irishka.movieapp.ui.movie.description.view.RelatedMoviesAdapter;
import com.example.irishka.movieapp.ui.movies.view.MoviesListActivity;
import com.example.irishka.movieapp.ui.movies.view.MoviesListAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DescriptionFragmentModule {

    @Provides
    @PerFragment
    static DescriptionPresenter providesDescriptionPresenter(IMoviesRepository moviesRepository, long movieId) {
        return new DescriptionPresenter(moviesRepository, movieId);
    }

    @Provides
    @PerFragment
    static CreatorsPresenter providesCreatorsPresenter(IMoviesRepository moviesRepository, long movieId) {
        return new CreatorsPresenter(moviesRepository, movieId);
    }

    @Provides
    @PerFragment
    static RelatedMoviesAdapter providesRelatedMoviesAdapter(DescriptionFragment descriptionFragment){
        return new RelatedMoviesAdapter(descriptionFragment);
    }

    @Provides
    @PerFragment
    static ActorsAdapter providesActorsAdapter(){
        return new ActorsAdapter();
    }

}
