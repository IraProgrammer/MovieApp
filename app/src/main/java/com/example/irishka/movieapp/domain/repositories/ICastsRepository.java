package com.example.irishka.movieapp.domain.repositories;

import com.example.irishka.movieapp.data.database.entity.CastOfMovie;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import io.reactivex.Single;

public interface ICastsRepository {

    Single<Cast> downloadConcreteCast(long movieId);

    Single<List<Cast>> downloadCasts(long movieId);

    void insertAllCoM(long id, List<Movie> movies);

    Single<List<CastOfMovie>> getMoviesOfCast(long id);
}
