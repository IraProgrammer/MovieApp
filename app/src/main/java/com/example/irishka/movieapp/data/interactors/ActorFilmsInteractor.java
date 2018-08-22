package com.example.irishka.movieapp.data.interactors;

import com.example.irishka.movieapp.data.database.CastsDbSource;
import com.example.irishka.movieapp.data.database.KeywordsDbSource;
import com.example.irishka.movieapp.data.database.MoviesDbSource;
import com.example.irishka.movieapp.data.database.entity.KeywordDb;
import com.example.irishka.movieapp.data.mappers.MoviesMapper;
import com.example.irishka.movieapp.data.network.CastsNetworkSource;
import com.example.irishka.movieapp.data.network.MoviesNetworkSource;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.domain.interactors.IActorFilmsInteractor;
import com.example.irishka.movieapp.domain.interactors.ISearchInteractor;
import com.example.irishka.movieapp.domain.repositories.ICastsRepository;
import com.example.irishka.movieapp.domain.repositories.IMoviesRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ActorFilmsInteractor implements IActorFilmsInteractor {

    private IMoviesRepository moviesRepository;

    private ICastsRepository castsRepository;

    @Inject
    ActorFilmsInteractor(IMoviesRepository moviesRepository, ICastsRepository castsRepository) {
        this.moviesRepository = moviesRepository;
        this.castsRepository = castsRepository;
    }


    private Single<List<Movie>> getActorFilmsFromInternet(long id) {
        return moviesRepository.getActorFilmsFromInternet(id)
                .doOnSuccess(movies -> castsRepository.insertAllCoM(id, movies))
                .doOnSuccess(movies -> moviesRepository.insertAllMovies(movies));
    }

    private Single<List<Movie>> getActorFilmsFromDatabase(long id) {
        return castsRepository.getMoviesOfCast(id)
                .flattenAsObservable(list -> list)
                .flatMapSingle(castOfMovie -> moviesRepository.getMovieFromDatabase(castOfMovie.getMovieId()))
                .toList()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Movie>> downloadActorFilms(long id) {
        return getActorFilmsFromInternet(id)
                .onErrorResumeNext(getActorFilmsFromDatabase(id));
    }
}
