package com.example.irishka.movieapp.domain.repositories;

import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import io.reactivex.Single;

public interface IMoviesRepository {

    Single<Movie> getMovieFromDatabase(long movieId);

    Single<Movie> downloadMovie(long movieId);

    Single<List<Movie>> downloadRelatedMovies(long movieId, int page);

    Single<List<Movie>> downloadMovies(int page, String type);

    Single<List<Movie>> getWithFilters(int page, String sort, String genres);

    Single<List<Movie>> getMoviesFromSearchFromInternet(String query, int page);

    Single<List<Movie>> getActorFilmsFromInternet(long id);

    void insertAllMovies(List<Movie> movies);
}
