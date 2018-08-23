package com.example.irishka.movieapp.data.mappers;

import android.content.Context;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.data.database.entity.MovieDb;
import com.example.irishka.movieapp.data.database.entity.MovieWithCategory;
import com.example.irishka.movieapp.data.database.entity.RelatedOfMovie;
import com.example.irishka.movieapp.data.models.BackdropModel;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.MovieModel;
import com.example.irishka.movieapp.data.models.TrailerModel;
import com.example.irishka.movieapp.domain.entity.Genre;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class MoviesMapper {

    private String seeSoon;

    private String TMDb;

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500//";

    private ImageMapper backdropMapper;

    private GenreMapper genreMapper;

    private ProductionCountryMapper productionCountryMapper;

    private TrailersMapper trailersMapper;

    @Inject
    MoviesMapper(ImageMapper backdropMapper, GenreMapper genreMapper,
                 ProductionCountryMapper productionCountryMapper, TrailersMapper trailersMapper, Context context) {
        this.backdropMapper = backdropMapper;
        this.genreMapper = genreMapper;
        this.productionCountryMapper = productionCountryMapper;
        this.trailersMapper = trailersMapper;

        seeSoon = context.getString(R.string.see_soon);
        TMDb = context.getString(R.string.TMDb);
    }

    public Movie apply(DescriptionModel descriptionModel, List<BackdropModel> backdrops, List<TrailerModel> trailers) {
        Movie movie = new Movie();
        movie.setId(descriptionModel.getId());
        movie.setPosterUrl(BASE_IMAGE_URL + descriptionModel.getPosterPath());
        movie.setReleaseDate(descriptionModel.getReleaseDate());
        movie.setTitle(descriptionModel.getTitle());

        if (!descriptionModel.getReleaseDate().equals("")) {

            if (Integer.compare(getYear(descriptionModel.getReleaseDate()), getYear(getDate())) == 1) {
                movie.setVoteAverageStr(seeSoon);
            } else {
                movie.setVoteAverageStr(TMDb + String.valueOf(descriptionModel.getVoteAverage()));
            }
        }

        movie.setVoteAverage(descriptionModel.getVoteAverage());
        movie.setOverview(descriptionModel.getOverview());
        movie.setAdult(descriptionModel.getAdult());
        movie.setGenres(genreMapper.mapGenresList(descriptionModel.getGenres()));
        movie.setRuntime(descriptionModel.getRuntime());
        movie.setCountries(productionCountryMapper.mapProductionCountryList(descriptionModel.getProductionCountries()));
        movie.setBackdrops(backdropMapper.mapBackdropsList(backdrops));
        movie.setTrailer(trailersMapper.mapTrailersListToOneTrailer(trailers));

        return movie;
    }


    private Movie applyForMovies(MovieModel movieModel) {
        Movie movie = new Movie();
        movie.setId(movieModel.getId());
        movie.setPosterUrl(BASE_IMAGE_URL + movieModel.getPosterPath());
        movie.setReleaseDate(movieModel.getReleaseDate());
        movie.setTitle(movieModel.getTitle());


        if (!movieModel.getReleaseDate().equals("")) {

            if (Integer.compare(getYear(movieModel.getReleaseDate()), getYear(getDate())) == 1) {
                movie.setVoteAverageStr(seeSoon);
            } else {
                movie.setVoteAverageStr(TMDb + String.valueOf(movieModel.getVoteAverage()));
            }

        }

        movie.setVoteAverage(movieModel.getVoteAverage());
        movie.setOverview(movieModel.getOverview());
        movie.setAdult(movieModel.getAdult());

        return movie;
    }


    public MovieDb applyToDb(Movie movie) {
        MovieDb movieDb = new MovieDb();
        movieDb.setId(movie.getId());
        movieDb.setPosterUrl(movie.getPosterUrl());
        movieDb.setReleaseDate(movie.getReleaseDate());
        movieDb.setTitle(movie.getTitle());
        movieDb.setOverview(movie.getOverview());

        if (!movie.getReleaseDate().equals("")) {

            if (Integer.compare(getYear(movie.getReleaseDate()), getYear(getDate())) == 1) {
                movieDb.setVoteAverageStr(seeSoon);
            } else {
                movieDb.setVoteAverageStr(TMDb + String.valueOf(movie.getVoteAverage()));
            }
        }

        movieDb.setVoteAverage(movie.getVoteAverage());
        movieDb.setAdult(movie.getAdult());
        movieDb.setRuntime(movie.getRuntime());

        if (movie.getCountries() != null) {
            movieDb.setCountries(productionCountryMapper.mapProductionCountryListToDb(movie.getCountries()));
        } else movieDb.setCountries(Collections.emptyList());
        if (movie.getBackdrops() != null)
            movieDb.setBackdrops(backdropMapper.mapBackdropsListToDb(movie.getBackdrops()));
        else movieDb.setBackdrops(Collections.emptyList());

        return movieDb;
    }

    public Movie applyFromDb(MovieDb movieDb, List<Genre> genres) {
        Movie movie = new Movie();
        movie.setId(movieDb.getId());
        movie.setPosterUrl(movieDb.getPosterUrl());
        movie.setReleaseDate(movieDb.getReleaseDate());
        movie.setTitle(movieDb.getTitle());
        movie.setOverview(movieDb.getOverview());

        if (!movieDb.getReleaseDate().equals("")) {

            if (Integer.compare(getYear(movieDb.getReleaseDate()), getYear(getDate())) == 1) {
                movie.setVoteAverageStr(seeSoon);
            } else {
                movie.setVoteAverageStr(TMDb + String.valueOf(movieDb.getVoteAverage()));
            }
        }

        movie.setVoteAverage(movieDb.getVoteAverage());
        movie.setAdult(movieDb.getAdult());
        movie.setGenres(genres);
        movie.setRuntime(movieDb.getRuntime());
        movie.setBackdrops(backdropMapper.mapBackdropsListFromDb(movieDb.getBackdrops()));
        movie.setCountries(productionCountryMapper.mapProductionCountryListFromDb(movieDb.getCountries()));

        return movie;
    }

    public List<Movie> mapMovies(List<MovieModel> movieModels) {
        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < movieModels.size(); i++) {
            movies.add(applyForMovies(movieModels.get(i)));
        }

        return movies;
    }

    public List<Movie> mapMoviesWithSort(List<MovieModel> movieModels) {

        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < movieModels.size(); i++) {
            movies.add(applyForMovies(movieModels.get(i)));
        }

     //   Collections.sort(movies, (first, second) -> Integer.compare(getYear(first), getYear(second)));

     //   Collections.reverse(movies);

        return movies;
    }

    private int getYear(Movie movie) {
        return Integer.parseInt(movie.getReleaseDate().split("-")[0]);
    }

    public List<MovieDb> mapMoviesListToDb(List<Movie> movies) {
        List<MovieDb> moviesDb = new ArrayList<>();

        for (int i = 0; i < movies.size(); i++) {
            moviesDb.add(applyToDb(movies.get(i)));
        }

        return moviesDb;
    }

    public List<RelatedOfMovie> createRoMList(long movieId, List<MovieModel> related) {
        List<RelatedOfMovie> relatedOfMovie = new ArrayList<>();

        for (int i = 0; i < related.size(); i++) {

            relatedOfMovie.add(new RelatedOfMovie(movieId, related.get(i).getId()));
        }

        return relatedOfMovie;

    }

    public List<MovieWithCategory> createMovieWithCategoryList(String type, List<MovieModel> movies) {

        List<MovieWithCategory> moviesWithCategory = new ArrayList<>();

        for (MovieModel movie : movies) {
            moviesWithCategory.add(new MovieWithCategory(type, movie.getId()));
        }

        return moviesWithCategory;

    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    private int getYear(String releaseDate) {
        return Integer.parseInt(releaseDate.split("-")[0]);
    }


}
