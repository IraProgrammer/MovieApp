package com.example.irishka.movieapp.data.network;

import com.example.irishka.movieapp.data.models.ActorInfoModel;
import com.example.irishka.movieapp.data.models.ActorPhotosModel;
import com.example.irishka.movieapp.data.models.BackdropModel;
import com.example.irishka.movieapp.data.models.CastModel;
import com.example.irishka.movieapp.data.models.CreditsModel;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.FilmsModel;
import com.example.irishka.movieapp.data.models.GalleryModel;
import com.example.irishka.movieapp.data.models.KeywordModel;
import com.example.irishka.movieapp.data.models.KeywordsPageModel;
import com.example.irishka.movieapp.data.models.MovieModel;
import com.example.irishka.movieapp.data.models.MoviePageModel;
import com.example.irishka.movieapp.data.models.TrailerListModel;
import com.example.irishka.movieapp.data.models.TrailerModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MoviesNetworkSource {

    private MoviesApi moviesApi;

    @Inject
    public MoviesNetworkSource(MoviesApi moviesApi) {
        this.moviesApi = moviesApi;
    }

    public Single<List<MovieModel>> getRelated(long movieId, int page){
        return moviesApi.getRelated(movieId, page)
                .map(MoviePageModel::getResults);
    }

    public Single<List<BackdropModel>> getBackdrops(long movieId){
        return moviesApi.getGallery(movieId)
                .map(GalleryModel::getBackdrops);
    }

    public Single<DescriptionModel> getDescription(long movieId){
        return moviesApi.getDescription(movieId);
    }

    public Single<List<TrailerModel>> getTrailers(long movieId) {
        return  moviesApi.getTrailers(movieId)
                .map(TrailerListModel::getResults); }

    public Single<List<MovieModel>> getMoviesFromSearch(String query, int page) {
        return  moviesApi.getMoviesFromSearch(query, page)
                .map(MoviePageModel::getResults); }

    public Single<List<MovieModel>> getNowPlaying(int page){
        return moviesApi.getNowPlaying(page)
                .map(MoviePageModel::getResults);
    }

    public Single<List<MovieModel>> getPopular(int page){
        return moviesApi.getPopular(page)
                .map(MoviePageModel::getResults);
    }

    public Single<List<MovieModel>> getTopRated(int page){
        return moviesApi.getTopRated(page)
                .map(MoviePageModel::getResults);
    }

    public Single<List<MovieModel>> getUpcoming(int page){
        return moviesApi.getUpcoming(page)
                .map(MoviePageModel::getResults);
    }

    public Single<List<MovieModel>> getWithFilters(int page, String sort, String genres){
        return moviesApi.getWithFilters(page, sort, genres)
                .map(MoviePageModel::getResults);
    }
}
