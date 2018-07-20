package com.example.irishka.movieapp.data.network;

import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.MoviePage;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApi {

    @GET("discover/movie/")
    Single<MoviePage> getMovies(@Query("page") int page);

    @GET("/movie/{movie_id}")
    Single<DescriptionModel> getDescription(@Path("movie_id") long movieId);

}
