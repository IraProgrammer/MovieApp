package com.example.irishka.movieapp.domain.repositories;

import com.example.irishka.movieapp.data.database.entity.CastOfMovie;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.CastListWithError;
import com.example.irishka.movieapp.domain.entity.CastWithError;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import io.reactivex.Single;

public interface ICastsRepository {

    Single<CastListWithError> downloadCasts(long movieId);

    Single<CastWithError> downloadConcreteCast(long movieId);

    void insertAllCoM(long id, List<Movie> movies);

    Single<List<CastOfMovie>> getMoviesOfCast(long id);
}
