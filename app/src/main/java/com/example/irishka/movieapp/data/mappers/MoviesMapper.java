package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.database.entity.MovieDb;
import com.example.irishka.movieapp.data.models.MovieModel;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MoviesMapper {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500//";

    @Inject
    public MoviesMapper() {

    }

    public Movie apply(MovieModel movieModel) {
        Movie movie = new Movie();
        movie.setBackdropPath(movieModel.getBackdropPath());
        movie.setId(movieModel.getId());
        movie.setPosterUrl(BASE_IMAGE_URL + movieModel.getPosterPath());
        movie.setReleaseDate(movieModel.getReleaseDate());
        movie.setVideo(movieModel.getVideo());
        movie.setTitle(movieModel.getTitle());
        movie.setVoteAverage(movieModel.getVoteAverage());
        movie.setAdult(movieModel.getAdult());

        return movie;
    }

    public MovieDb applyToDb(MovieModel movieModel) {
        MovieDb movie = new MovieDb();
        movie.setBackdropPath(movieModel.getBackdropPath());
        movie.setId(movieModel.getId());
        movie.setPosterUrl(BASE_IMAGE_URL + movieModel.getPosterPath());
        movie.setReleaseDate(movieModel.getReleaseDate());
        movie.setVideo(movieModel.getVideo());
        movie.setTitle(movieModel.getTitle());
        movie.setVoteAverage(movieModel.getVoteAverage());
        movie.setAdult(movieModel.getAdult());

        return movie;
    }

    public Movie applyFromDb(MovieDb movieDb) {
        Movie movie = new Movie();
        movie.setBackdropPath(movieDb.getBackdropPath());
        movie.setId(movieDb.getId());
        movie.setPosterUrl(movieDb.getPosterUrl());
        movie.setReleaseDate(movieDb.getReleaseDate());
        movie.setVideo(movieDb.getVideo());
        movie.setTitle(movieDb.getTitle());
        movie.setVoteAverage(movieDb.getVoteAverage());
        movie.setAdult(movieDb.getAdult());

        return movie;
    }

    public List<Movie> mapMoviesList(List<MovieModel> movieModels){
        List<Movie> movies = new ArrayList<>();

        for (MovieModel movieModel: movieModels) {
            movies.add(apply(movieModel));
        }

        return movies;
    }

    public List<MovieDb> mapMoviesListToDb(List<MovieModel> movieModels){
        List<MovieDb> movies = new ArrayList<>();

        for (MovieModel movieModel: movieModels) {
            movies.add(applyToDb(movieModel));
        }

        return movies;
    }

    public List<Movie> mapMoviesListFromDb(List<MovieDb> moviesDb){
        List<Movie> movies = new ArrayList<>();

        for (MovieDb movieDb: moviesDb) {
            movies.add(applyFromDb(movieDb));
        }

        return movies;
    }
}
