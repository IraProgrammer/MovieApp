package com.example.irishka.movieapp.di.module;

import com.example.irishka.movieapp.data.database.converters.ActorFilmsConverter;
import com.example.irishka.movieapp.data.interactors.ActorFilmsInteractor;
import com.example.irishka.movieapp.data.interactors.SearchInteractor;
import com.example.irishka.movieapp.data.repositories.CastsRepository;
import com.example.irishka.movieapp.data.repositories.KeywordsRepository;
import com.example.irishka.movieapp.data.repositories.MoviesRepository;
import com.example.irishka.movieapp.domain.entity.Keyword;
import com.example.irishka.movieapp.domain.interactors.IActorFilmsInteractor;
import com.example.irishka.movieapp.domain.interactors.ISearchInteractor;
import com.example.irishka.movieapp.domain.repositories.ICastsRepository;
import com.example.irishka.movieapp.domain.repositories.IKeywordsRepository;
import com.example.irishka.movieapp.domain.repositories.IMoviesRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepModule {

    @Binds
    @Singleton
    abstract IMoviesRepository provideMoviesRepository(MoviesRepository moviesRepository);

    @Binds
    @Singleton
    abstract IKeywordsRepository provideKeywordsRepository(KeywordsRepository keywordsRepository);

    @Binds
    @Singleton
    abstract ICastsRepository provideCastsRepository(CastsRepository castsRepository);

    @Binds
    @Singleton
    abstract ISearchInteractor provideSearchInteractor(SearchInteractor searchInteractor);

    @Binds
    @Singleton
    abstract IActorFilmsInteractor provideActorFilmsInteractor(ActorFilmsInteractor actorFilmsInteractor);

}
