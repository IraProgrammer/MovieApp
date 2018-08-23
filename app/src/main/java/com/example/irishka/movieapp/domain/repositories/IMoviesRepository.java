package com.example.irishka.movieapp.domain.repositories;

import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.domain.entity.MoviesListWithError;

import java.util.List;

import io.reactivex.Single;

public interface IMoviesRepository {

    Single<Movie> getMovieFromDatabase(long movieId);

    Single<Movie> downloadMovie(long movieId);

    Single<List<Movie>> downloadRelatedMovies(long movieId, int page);

    Single<MoviesListWithError> downloadMovies(int page, String type);

    Single<MoviesListWithError> getWithFilters(int page, String sort, String genres);

    Single<MoviesListWithError> getMoviesFromSearchFromInternet(String query, int page);

    Single<List<Movie>> getActorFilmsFromInternet(long id);

    void insertAllMovies(List<Movie> movies);
}
