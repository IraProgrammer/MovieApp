package com.example.irishka.movieapp.domain.repository;

import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import io.reactivex.Single;

public interface IMoviesRepository {
    Single<List<Movie>> downloadMovies(int page);

    Single<Cast> downloadConcreteCast(long movieId);

    Single<Movie> downloadMovie(long movieId);

    Single<List<Movie>> downloadRelatedMovies(long movieId, int page);

    Single<List<Cast>> downloadCasts(long movieId);

    Single<List<Movie>> downloadActorFilms(long id);
}
