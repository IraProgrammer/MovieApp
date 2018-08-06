package com.example.irishka.movieapp.data.database;

import com.example.irishka.movieapp.data.database.dao.CastDao;
import com.example.irishka.movieapp.data.database.dao.CastOfMovieDao;
import com.example.irishka.movieapp.data.database.dao.GenreDao;
import com.example.irishka.movieapp.data.database.dao.GenreOfMovieDao;
import com.example.irishka.movieapp.data.database.dao.MovieDao;
import com.example.irishka.movieapp.data.database.dao.RelatedOfMovieDao;
import com.example.irishka.movieapp.data.database.entity.CastDb;
import com.example.irishka.movieapp.data.database.entity.CastOfMovie;
import com.example.irishka.movieapp.data.database.entity.GenreDb;
import com.example.irishka.movieapp.data.database.entity.GenreOfMovie;
import com.example.irishka.movieapp.data.database.entity.MovieDb;
import com.example.irishka.movieapp.data.database.entity.RelatedOfMovie;
import com.example.irishka.movieapp.data.mappers.CastMapper;
import com.example.irishka.movieapp.data.mappers.GenreMapper;
import com.example.irishka.movieapp.data.mappers.MoviesMapper;
import com.example.irishka.movieapp.data.network.MoviesApi;
import com.example.irishka.movieapp.domain.entity.Genre;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MoviesDbSource {

    private MovieDao movieDao;

    private CastDao castDao;

    private GenreDao genreDao;

    private GenreOfMovieDao genreOfMovieDao;

    private CastOfMovieDao castOfMovieDao;

    private RelatedOfMovieDao relatedOfMovieDao;

    @Inject
    public MoviesDbSource(MovieDao movieDao, CastDao castDao, GenreDao genreDao,
                          GenreOfMovieDao genreOfMovieDao, CastOfMovieDao castOfMovieDao,
                          RelatedOfMovieDao relatedOfMovieDao) {
        this.movieDao = movieDao;
        this.castDao = castDao;
        this.genreDao = genreDao;
        this.genreOfMovieDao = genreOfMovieDao;
        this.castOfMovieDao = castOfMovieDao;
        this.relatedOfMovieDao = relatedOfMovieDao;
    }

    public void insertAllMovies(List<MovieDb> moviesDb) {
        movieDao.insertAll(moviesDb);
    }

    public Single<List<MovieDb>> getAllMovies() {
        return movieDao.getAllMovies();
    }

//    public Single<List<MovieDb>> getRelatedMovies(long movieId) {
//        return movieDao.getRelatedMovies(movieId);
//    }

    public Single<List<GenreOfMovie>> getGenresOfMovie(long movieId) {
        return genreOfMovieDao.getGenresOfMovie(movieId);
    }

    public Single<GenreDb> getGenre(long genreId) {
        return genreDao.getGenre(genreId);
    }

    public void insertAllCasts(List<CastDb> castsDb) {
        castDao.insertAll(castsDb);
    }

    public void insertCast(CastDb castDb) {
        castDao.insert(castDb);
    }

    public Single<List<CastOfMovie>> getCastsOfMovie(long movieId) {
        return castOfMovieDao.getCastOfMovie(movieId);
    }

    public Single<CastDb> getCast(long id) {
        return castDao.getCast(id);
    }

    public void insertAllGenres(List<GenreDb> genresDb) {
        genreDao.insert(genresDb);
    }

    public void insertAllGoM(List<GenreOfMovie> genresOfMovie) {
        genreOfMovieDao.trans(genresOfMovie);
    }

    public void insertAllCoM(List<CastOfMovie> castsOfMovie) {
        castOfMovieDao.trans(castsOfMovie);
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
}
