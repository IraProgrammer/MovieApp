package com.example.irishka.movieapp.ui.movie.creators.actor.di;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.movie.creators.actor.ActorActivity;
import com.example.irishka.movieapp.ui.movie.creators.actor.info.InfoFragment;
import com.example.irishka.movieapp.ui.movie.creators.actor.info.InfoPresenter;
import com.example.irishka.movieapp.ui.movie.creators.actor.info.PhotosAdapter;
import com.example.irishka.movieapp.ui.movie.creators.view.ActorsAdapter;

import dagger.Module;
import dagger.Provides;

import static com.example.irishka.movieapp.ui.movie.creators.view.CreatorsFragment.PERSON_ID;

@Module
public abstract class InfoFragmentModule {

    @Provides
    @PerFragment
    static InfoPresenter providesInfoPresenter(IMoviesRepository moviesRepository, long id) {
        return new InfoPresenter(moviesRepository, id);
    }
}
