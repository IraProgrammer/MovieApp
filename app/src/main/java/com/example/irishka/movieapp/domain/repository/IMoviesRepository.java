package com.example.irishka.movieapp.domain.repository;

import com.example.irishka.movieapp.data.models.ActorInfoModel;
import com.example.irishka.movieapp.data.models.ActorPhotosModel;
import com.example.irishka.movieapp.data.models.BackdropModel;
import com.example.irishka.movieapp.data.models.CreditsModel;
import com.example.irishka.movieapp.data.models.MovieModel;
import com.example.irishka.movieapp.data.models.TrailerModel;
import com.example.irishka.movieapp.domain.entity.Backdrop;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import io.reactivex.Single;

public interface IMoviesRepository {
    Single<List<Movie>> downloadMovies(int page);

    Single<Movie> downloadMovie(long movieId);

    Single<List<Movie>> downloadRelatedMovies(long movieId, int page);

    Single<List<Cast>> downloadCasts(long movieId);

    // TODO: модели data слоя не должны выходить из репозитория
    // мапай в модели domain слоя

    Single<ActorPhotosModel> getActorPhotoModel(long castId);

    Single<ActorInfoModel> getActorInfoModel(long castId);

    Single<List<MovieModel>> getActorFilms(long movieId);
}
