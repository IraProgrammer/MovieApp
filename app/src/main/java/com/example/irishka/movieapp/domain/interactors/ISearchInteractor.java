package com.example.irishka.movieapp.domain.interactors;

import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import io.reactivex.Single;

public interface ISearchInteractor {

    Single<List<String>> getKeywordsFromInternet(String query);

    Single<List<String>> getKeywordsFromDb();

    Single<List<Movie>> getMoviesFromSearchFromInternet(String query, int page);

}
