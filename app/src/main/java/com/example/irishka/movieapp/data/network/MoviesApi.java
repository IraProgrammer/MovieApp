package com.example.irishka.movieapp.data.network;

import com.example.irishka.movieapp.data.models.ActorInfoModel;
import com.example.irishka.movieapp.data.models.ActorPhotosModel;
import com.example.irishka.movieapp.data.models.CreditsModel;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.FilmsModel;
import com.example.irishka.movieapp.data.models.GalleryModel;
import com.example.irishka.movieapp.data.models.KeywordsPageModel;
import com.example.irishka.movieapp.data.models.MovieModel;
import com.example.irishka.movieapp.data.models.MoviePageModel;
import com.example.irishka.movieapp.data.models.TrailerListModel;

import java.util.List;

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
    Single<MoviePageModel> getRelated(@Path("movie_id") long movieId, @Query("page") int page);

    @GET("movie/{movie_id}/credits")
    Single<CreditsModel> getCreators(@Path("movie_id") long movieId);

    @GET("movie/{movie_id}/images")
    Single<GalleryModel> getGallery(@Path("movie_id") long movieId);

    @GET("person/{id}/images")
    Single<ActorPhotosModel> getActorPhotos(@Path("id") long id);

    @GET("person/{id}")
    Single<ActorInfoModel> getActorInfo(@Path("id") long id);

    @GET("person/{id}/movie_credits")
    Single<FilmsModel> getActorFilms(@Path("id") long id);

    @GET("movie/{movie_id}/videos")
    Single<TrailerListModel> getTrailers(@Path("movie_id") long movieId);

    @GET("search/movie/")
    Single<MoviePageModel> getMoviesFromSearch(@Query("query") String query, @Query("page") int page);

    @GET("search/keyword")
    Single<KeywordsPageModel> getKeywords(@Query("query") String query);

    @GET("movie/now_playing/")
    Single<MoviePageModel> getNowPlaying(@Query("page") int page);

    @GET("movie/popular/")
    Single<MoviePageModel> getPopular(@Query("page") int page);

    @GET("movie/top_rated/")
    Single<MoviePageModel> getTopRated(@Query("page") int page);

    @GET("movie/upcoming/")
    Single<MoviePageModel> getUpcoming(@Query("page") int page);
}
