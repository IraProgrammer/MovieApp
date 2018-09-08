package com.example.irishka.movieapp.domain.repositories;

import com.example.irishka.movieapp.data.database.entity.MovieIdsFromSearch;
import com.example.irishka.movieapp.domain.MainType;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.domain.entity.MovieWithError;
import com.example.irishka.movieapp.domain.entity.MoviesListWithError;

import java.util.List;

import io.reactivex.Single;

public interface IMoviesRepository {

    Single<List<Movie>> downloadRelatedMovies(long movieId, int page);

    Single<MovieWithError> getMovieFromDatabase(long movieId);

    Single<MovieWithError> downloadMovie(long movieId, boolean isSearch);

    Single<MoviesListWithError> downloadMovies(int page, MainType type);

    Single<MoviesListWithError> getWithFilters(int page, String sort, String genres);

    Single<MoviesListWithError> getMoviesFromSearchFromInternet(String query, int page);

    Single<List<Movie>> getMoviesForSearchFromDatabase();

    Single<List<Movie>> getActorFilmsFromInternet(long id);

    void insertAllMovies(List<Movie> movies);
}
