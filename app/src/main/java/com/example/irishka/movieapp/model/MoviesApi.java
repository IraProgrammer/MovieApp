package com.example.irishka.movieapp.model;

import com.example.irishka.movieapp.BuildConfig;
import com.example.irishka.movieapp.model.Pojo.MoviePage;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApi {

    @GET("discover/movie/?api_key=" + BuildConfig.MOVIE_API_KEY)
    Single<MoviePage> getMovies(@Query("page") int page);

}
