package com.example.irishka.movieapp.domain.interactors;

import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import io.reactivex.Single;

public interface IActorFilmsInteractor {

    Single<List<Movie>> downloadActorFilms(long id);

}
