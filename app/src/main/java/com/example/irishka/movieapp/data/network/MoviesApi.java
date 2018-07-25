package com.example.irishka.movieapp.data.network;

import com.example.irishka.movieapp.data.models.CreditsModel;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.GalleryModel;
import com.example.irishka.movieapp.data.models.MoviePageModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApi {

    @GET("discover/movie/")
    Single<MoviePageModel> getMovies(@Query("page") int page);

    @GET("movie/{movie_id}")
    Single<DescriptionModel> getDescription(@Path("movie_id") long movieId);

    @GET("movie/{movie_id}/similar")
    Single<MoviePageModel> getRelated(@Path("movie_id") long movieId);

    @GET("movie/{movie_id}/credits")
    Single<CreditsModel> getCreators(@Path("movie_id") long movieId);

    @GET("movie/{movie_id}/images")
    Single<GalleryModel> getGallery(@Path("movie_id") long movieId);

}
