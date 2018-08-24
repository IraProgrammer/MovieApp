package com.example.irishka.movieapp.data.database;

import com.example.irishka.movieapp.data.database.dao.MovieDao;
import com.example.irishka.movieapp.data.database.dao.MovieWithCategoryDao;
import com.example.irishka.movieapp.data.database.dao.RelatedOfMovieDao;
import com.example.irishka.movieapp.data.database.entity.MovieDb;
import com.example.irishka.movieapp.data.database.entity.MovieWithCategory;
import com.example.irishka.movieapp.data.database.entity.RelatedOfMovie;
import com.example.irishka.movieapp.domain.MainType;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MoviesDbSource {

    private MovieDao movieDao;

    private RelatedOfMovieDao relatedOfMovieDao;

    private MovieWithCategoryDao movieWithCategoryDao;

    @Inject
    public MoviesDbSource(MovieDao movieDao, RelatedOfMovieDao relatedOfMovieDao,
                          MovieWithCategoryDao movieWithCategoryDao) {
        this.movieDao = movieDao;
        this.relatedOfMovieDao = relatedOfMovieDao;
        this.movieWithCategoryDao = movieWithCategoryDao;
    }

    public void insertAllMovies(List<MovieDb> moviesDb) {
        movieDao.insertAll(moviesDb);
    }

    public Single<List<MovieDb>> getAllMovies() {
        return movieDao.getAllMovies();
    }

    public void insertMovie(MovieDb movieDb) {
        movieDao.insert(movieDb);
    }

    public Single<MovieDb> getMovie(long movieId) {
        return movieDao.getMovie(movieId);
    }

    public Single<List<RelatedOfMovie>> getRelatedOfMovie(long movieId) {
        return relatedOfMovieDao.getRelatedOfMovie(movieId);
    }

    public void insertAllRoM(List<RelatedOfMovie> relatedOfMovies) {
        relatedOfMovieDao.trans(relatedOfMovies);
    }

    public Single<List<MovieWithCategory>> getMovieWithCategory(MainType type){
        return movieWithCategoryDao.getMoviesWithCategory(type);
    }

    public void insertMoviesWithCategory(List<MovieWithCategory> moviesWithCategoriy){
        movieWithCategoryDao.trans(moviesWithCategoriy);
    }
}
