package com.example.irishka.movieapp.domain.interactors;

import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.domain.entity.MoviesListWithError;

import java.util.List;

import io.reactivex.Single;

public interface ISearchInteractor {

    Single<List<String>> getKeywordsFromInternet(String query);

    Single<List<String>> getKeywordsFromDb();

    Single<MoviesListWithError> getMoviesFromSearchFromInternet(String query, int page);

    Single<List<Movie>> getMoviesForSearchFromDatabase();
}
