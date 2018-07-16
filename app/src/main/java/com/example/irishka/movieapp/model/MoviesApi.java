package com.example.irishka.movieapp.model;

import com.example.irishka.movieapp.BuildConfig;
import com.example.irishka.movieapp.model.Pojo.MoviePage;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MoviesApi {

    @GET("discover/movie/?api_key=" + BuildConfig.MOVIE_API_KEY + "&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    Observable<MoviePage> getMovies();

}
