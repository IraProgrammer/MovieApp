package com.example.irishka.movieapp.data.mapper;

import com.example.irishka.movieapp.data.MovieModel;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class MoviesMapper implements Function<MovieModel, Movie>{

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500//";

    @Inject
    public MoviesMapper() {

    }

    @Override
    public Movie apply(MovieModel movieModel) {
        Movie movie = new Movie();
        movie.setBackdropPath(movieModel.getBackdropPath());
        movie.setId(movieModel.getId());
        movie.setOriginalLanguage(movieModel.getOriginalLanguage());
        movie.setOriginalTitle(movieModel.getOriginalTitle());
        movie.setPopularity(movieModel.getPopularity());
        movie.setPosterPath(BASE_IMAGE_URL + movieModel.getPosterPath());
        movie.setReleaseDate(movieModel.getReleaseDate());
        movie.setVideo(movieModel.getVideo());
        movie.setTitle(movieModel.getTitle());

        return movie;
    }

    // TODO: скорее mapList
    public List<Movie> getMoviesList(List<MovieModel> movieModels){
        List<Movie> movies = new ArrayList<>();

        for (MovieModel movieModel: movieModels) {
            movies.add(apply(movieModel));
        }

        return movies;
    }
}
