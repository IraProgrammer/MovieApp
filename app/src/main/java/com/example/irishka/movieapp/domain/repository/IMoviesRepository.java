package com.example.irishka.movieapp.domain.repository;

import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import io.reactivex.Single;

public interface IMoviesRepository {

 //   void onSaveMovies(List<MovieModel> movies);

 //   Single<List<MovieModel>> getMoviesFromInternet();

 //   Single<List<MovieModel>> getMoviesFromDatabase();

    Single<List<Movie>> downloadMovies();


}
