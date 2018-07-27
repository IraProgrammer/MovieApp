package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.database.entity.MovieDb;
import com.example.irishka.movieapp.data.models.BackdropModel;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.GalleryModel;
import com.example.irishka.movieapp.data.models.MovieModel;
import com.example.irishka.movieapp.domain.entity.Backdrop;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Genre;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MoviesMapper {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500//";

    @Inject
    public MoviesMapper() {

    }

    private BackdropMapper backdropMapper;

    private GenreMapper genreMapper;

    private ProductionCountryMapper productionCountryMapper;

    @Inject
    public MoviesMapper(BackdropMapper backdropMapper, GenreMapper genreMapper,
                        ProductionCountryMapper productionCountryMapper) {
        this.backdropMapper = backdropMapper;
        this.genreMapper = genreMapper;
        this.productionCountryMapper = productionCountryMapper;
    }

    public Movie apply(DescriptionModel descriptionModel, List<BackdropModel> backdrops) {
        Movie movie = new Movie();
        movie.setId(descriptionModel.getId());
        movie.setPosterUrl(BASE_IMAGE_URL + descriptionModel.getPosterPath());
        movie.setReleaseDate(descriptionModel.getReleaseDate());
        movie.setTitle(descriptionModel.getTitle());
        movie.setVoteAverage(descriptionModel.getVoteAverage());
        movie.setAdult(descriptionModel.getAdult());
        movie.setGenres(genreMapper.mapGenresList(descriptionModel.getGenres()));
        movie.setRuntime(descriptionModel.getRuntime());
        movie.setCountries(productionCountryMapper.mapProductionCountryList(descriptionModel.getProductionCountries()));
        movie.setBackdrops(backdropMapper.mapBackdropsList(backdrops));

        return movie;
    }

    public MovieDb applyToDb(Movie movie) {
        MovieDb movieDb = new MovieDb();
        movieDb.setId(movie.getId());
        movieDb.setPosterUrl(movie.getPosterUrl());
        movieDb.setReleaseDate(movie.getReleaseDate());
        movieDb.setTitle(movie.getTitle());
        movieDb.setVoteAverage(movie.getVoteAverage());
        movieDb.setAdult(movie.getAdult());
        movieDb.setRuntime(movie.getRuntime());
        movieDb.setCountries(productionCountryMapper.mapProductionCountryListToDb(movie.getCountries()));
        movieDb.setBackdrops(backdropMapper.mapBackdropsListToDb(movie.getBackdrops()));

        return movieDb;
    }

    public Movie applyFromDb(MovieDb movieDb, List<Genre> genres) {
        Movie movie = new Movie();
        movie.setId(movieDb.getId());
        movie.setPosterUrl(movieDb.getPosterUrl());
        movie.setReleaseDate(movieDb.getReleaseDate());
        movie.setTitle(movieDb.getTitle());
        movie.setVoteAverage(movieDb.getVoteAverage());
        movie.setAdult(movieDb.getAdult());
        movie.setGenres(genres);
        movie.setRuntime(movieDb.getRuntime());
        movie.setBackdrops(backdropMapper.mapBackdropsListFromDb(movieDb.getBackdrops()));
        movie.setCountries(productionCountryMapper.mapProductionCountryListFromDb(movieDb.getCountries()));

        return movie;
    }

//    public Movie applyFromDb(MovieDb movieDb, List<Cast> casts) {
//        Movie movie = new Movie();
//        movie.setId(movieDb.getId());
//        movie.setPosterUrl(movieDb.getPosterUrl());
//        movie.setReleaseDate(movieDb.getReleaseDate());
//        movie.setTitle(movieDb.getTitle());
//        movie.setVoteAverage(movieDb.getVoteAverage());
//        movie.setAdult(movieDb.getAdult());
//        movie.setCasts(casts);
//        movie.setRuntime(movieDb.getRuntime());
//        movie.setBackdrops(backdropMapper.mapBackdropsListFromDb(movieDb.getBackdrops()));
//        movie.setCountries(productionCountryMapper.mapProductionCountryListFromDb(movieDb.getCountries()));
//
//        return movie;
//    }

//    public List<Movie> mapMoviesList(List<MovieModel> movieModels, List<DescriptionModel> descriptionModels,
//                                     List<BackdropModel> backdropModels){
//        List<Movie> movies = new ArrayList<>();
//
//        for (int i = 0; i < movieModels.size(); i++) {
//            movies.add(apply(movieModels.get(i), descriptionModels.get(i), backdropModels));
//        }
//
//        return movies;
//    }

    public List<MovieDb> mapMoviesListToDb(List<Movie> movies){
        List<MovieDb> moviesDb = new ArrayList<>();

        for (int i = 0; i < movies.size(); i++) {
            moviesDb.add(applyToDb(movies.get(i)));
        }

        return moviesDb;
    }

//    public List<Movie> mapMoviesListFromDb(List<MovieDb> moviesDb){
//        List<Movie> movies = new ArrayList<>();
//
//        for (MovieDb movieDb: moviesDb) {
//            movies.add(applyFromDb(movieDb));
//        }
//
//        return movies;
//    }
}
