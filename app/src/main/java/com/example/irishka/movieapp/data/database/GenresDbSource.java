package com.example.irishka.movieapp.data.database;

import com.example.irishka.movieapp.data.database.dao.GenreDao;
import com.example.irishka.movieapp.data.database.dao.GenreOfMovieDao;
import com.example.irishka.movieapp.data.database.dao.KeywordsDao;
import com.example.irishka.movieapp.data.database.dao.MovieDao;
import com.example.irishka.movieapp.data.database.dao.MovieWithCategoryDao;
import com.example.irishka.movieapp.data.database.dao.RelatedOfMovieDao;
import com.example.irishka.movieapp.data.database.entity.GenreDb;
import com.example.irishka.movieapp.data.database.entity.GenreOfMovie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class GenresDbSource {

    private GenreDao genreDao;

    private GenreOfMovieDao genreOfMovieDao;

    @Inject
    public GenresDbSource(GenreDao genreDao, GenreOfMovieDao genreOfMovieDao) {
        this.genreDao = genreDao;
        this.genreOfMovieDao = genreOfMovieDao;
    }

    public Single<List<GenreOfMovie>> getGenresOfMovie(long movieId) {
        return genreOfMovieDao.getGenresOfMovie(movieId);
    }

    public Single<GenreDb> getGenre(long genreId) {
        return genreDao.getGenre(genreId);
    }

    public void insertAllGenres(List<GenreDb> genresDb) {
        genreDao.insert(genresDb);
    }

    public void insertAllGoM(List<GenreOfMovie> genresOfMovie) {
        genreOfMovieDao.trans(genresOfMovie);
    }
}
