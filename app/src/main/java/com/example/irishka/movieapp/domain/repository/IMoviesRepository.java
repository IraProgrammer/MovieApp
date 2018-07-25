package com.example.irishka.movieapp.domain.repository;

import com.example.irishka.movieapp.data.models.BackdropModel;
import com.example.irishka.movieapp.data.models.CreditsModel;
import com.example.irishka.movieapp.domain.entity.Backdrop;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Description;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import io.reactivex.Single;

public interface IMoviesRepository {
    Single<List<Movie>> downloadMovies(int page);

    Single<Description> downloadDescription(long movieId);

    Single<List<Movie>> downloadRelatedMovies(long movieId);

    Single<List<Cast>> downloadCasts(long movieId);

    Single<List<Backdrop>> downloadGallery(long movieId);

}
