package com.example.irishka.movieapp.domain.repository;

import com.example.irishka.movieapp.data.models.MovieModel;
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

    Single<List<Movie>> getMoviesFromSearchFromInternet(String query, int page);

    Single<List<String>> getKeywordsFromInternet(String query);

    Single<List<String>> getKeywordsFromDb();

    Single<List<Movie>> downloadMoviesForMainScreen(int page, String type);

    Single<List<Movie>> getWithFilters(String filters);
}
