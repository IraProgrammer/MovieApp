package com.example.irishka.movieapp.data.network;

import com.example.irishka.movieapp.data.models.ActorInfoModel;
import com.example.irishka.movieapp.data.models.ActorPhotosModel;
import com.example.irishka.movieapp.data.models.CastModel;
import com.example.irishka.movieapp.data.models.CreditsModel;
import com.example.irishka.movieapp.data.models.FilmsModel;
import com.example.irishka.movieapp.data.models.MovieModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CastsNetworkSource {

    private MoviesApi moviesApi;

    @Inject
    public CastsNetworkSource(MoviesApi moviesApi) {
        this.moviesApi = moviesApi;
    }

    public Single<List<CastModel>> getCasts(long movieId){
        return moviesApi.getCreators(movieId)
                .map(CreditsModel::getCast);
    }

    public Single<ActorPhotosModel> getActorPhotos(long id){
        return moviesApi.getActorPhotos(id);
    }

    public Single<ActorInfoModel> getActorInfo(long id){
        return moviesApi.getActorInfo(id);
    }

    public Single<List<MovieModel>> getActorFilms(long id){
        return moviesApi.getActorFilms(id)
                .map(FilmsModel::getMovies);
    }
}
