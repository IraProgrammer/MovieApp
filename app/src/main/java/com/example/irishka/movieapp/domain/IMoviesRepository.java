package com.example.irishka.movieapp.domain;

import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import io.reactivex.Single;

// TODO: стоит так же создать package с именем repository в domain и хранить интерфейс там
public interface IMoviesRepository {

 //   void onSaveMovies(List<MovieModel> movies);

 //   Single<List<MovieModel>> getMoviesFromInternet();

 //   Single<List<MovieModel>> getMoviesFromDatabase();

    Single<List<Movie>> downloadMovies();


}
