package com.example.irishka.movieapp.data.network;

import com.example.irishka.movieapp.data.MoviePage;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApi {

    @GET("discover/movie/")
    Single<MoviePage> getMovies(@Query("page") int page);

}
