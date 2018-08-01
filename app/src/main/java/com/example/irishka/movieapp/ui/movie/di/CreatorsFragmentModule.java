package com.example.irishka.movieapp.ui.movie.di;

import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.movie.creators.actor.films.view.FilmsAdapter;
import com.example.irishka.movieapp.ui.movie.creators.actor.info.InfoFragment;
import com.example.irishka.movieapp.ui.movie.creators.presenter.CreatorsPresenter;
import com.example.irishka.movieapp.ui.movie.creators.view.ActorsAdapter;
import com.example.irishka.movieapp.ui.movie.creators.view.CreatorsFragment;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class CreatorsFragmentModule {

    @Provides
    @PerFragment
    static ActorsAdapter provideActorsAdapter(CreatorsFragment creatorsFragment){
        return new ActorsAdapter(creatorsFragment);
    }
}
