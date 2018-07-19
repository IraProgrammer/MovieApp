package com.example.irishka.movieapp.data.repository;

import com.example.irishka.movieapp.data.MovieModel;
import com.example.irishka.movieapp.data.database.MovieDao;
import com.example.irishka.movieapp.data.mapper.MoviesMapper;
import com.example.irishka.movieapp.data.MoviePage;
import com.example.irishka.movieapp.data.network.MoviesApi;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class MoviesRepository implements IMoviesRepository {

    private MoviesMapper moviesMapper;

    private MovieDao movieDao;

    private MoviesApi moviesApi;

    private int page = 1;

    @Inject
    public MoviesRepository(MoviesMapper moviesMapper,
                            MovieDao movieDao, MoviesApi moviesApi) {
        this.moviesMapper = moviesMapper;
        this.movieDao = movieDao;
        this.moviesApi = moviesApi;
    }

    private void onSaveMovies(List<MovieModel> movies) {
        // TODO: тут всего одна строчка, можно ее и вставлять в doOnSuccess
        movieDao.insertAll(movies);
    }

    private Single<List<Movie>> getMoviesFromInternet() {
        return moviesApi
                .getMovies(page)
                .map(MoviePage::getResults)
                .doOnSuccess(this::onSaveMovies)
                .doOnSuccess(movies -> page++)
                .map(movies -> moviesMapper.getMoviesList(movies));
    }

    private Single<List<Movie>> getMoviesFromDatabase() {
        return movieDao.getAllMovies()
                .map(movies -> moviesMapper.getMoviesList(movies))
                .subscribeOn(Schedulers.io());
    }

    public Single<List<Movie>> downloadMovies() {
        return getMoviesFromInternet()
                .onErrorResumeNext(getMoviesFromDatabase());
    }
}
