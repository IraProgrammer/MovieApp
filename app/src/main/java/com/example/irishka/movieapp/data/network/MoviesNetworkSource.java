package com.example.irishka.movieapp.data.network;

import com.example.irishka.movieapp.data.database.dao.CastDao;
import com.example.irishka.movieapp.data.database.dao.CastOfMovieDao;
import com.example.irishka.movieapp.data.database.dao.GenreDao;
import com.example.irishka.movieapp.data.database.dao.GenreOfMovieDao;
import com.example.irishka.movieapp.data.database.dao.MovieDao;
import com.example.irishka.movieapp.data.database.entity.MovieDb;
import com.example.irishka.movieapp.data.models.ActorInfoModel;
import com.example.irishka.movieapp.data.models.ActorPhotosModel;
import com.example.irishka.movieapp.data.models.CreditsModel;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.FilmsModel;
import com.example.irishka.movieapp.data.models.GalleryModel;
import com.example.irishka.movieapp.data.models.MovieModel;
import com.example.irishka.movieapp.data.models.MoviePageModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MoviesNetworkSource {

    private MoviesApi moviesApi;

    @Inject
    public MoviesNetworkSource(MoviesApi moviesApi) {
        this.moviesApi = moviesApi;
    }

    public Single<MoviePageModel> getMovies(int page){
        return moviesApi.getMovies(page);
    }

    public Single<MoviePageModel> getRelated(long movieId){
        return moviesApi.getRelated(movieId);
    }

    public Single<CreditsModel> getCreators(long movieId){
        return moviesApi.getCreators(movieId);
    }

    public Single<GalleryModel> getGallery(long movieId){
        return moviesApi.getGallery(movieId);
    }

    public Single<DescriptionModel> getDescription(long movieId){
        return moviesApi.getDescription(movieId);
    }

    public Single<ActorPhotosModel> getActorPhotos(long id){
        return moviesApi.getActorPhotos(id);
    }

    public Single<ActorInfoModel> getActorInfo(long id){
        return moviesApi.getActorInfo(id);
    }

    public Single<FilmsModel> getActorFilms(long id){
        return moviesApi.getActorFilms(id);
    }
}
