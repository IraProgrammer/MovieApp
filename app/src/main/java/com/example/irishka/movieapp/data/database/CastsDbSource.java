package com.example.irishka.movieapp.data.database;

import com.example.irishka.movieapp.data.database.dao.CastDao;
import com.example.irishka.movieapp.data.database.dao.CastOfMovieDao;
import com.example.irishka.movieapp.data.database.dao.GenreDao;
import com.example.irishka.movieapp.data.database.dao.GenreOfMovieDao;
import com.example.irishka.movieapp.data.database.dao.KeywordsDao;
import com.example.irishka.movieapp.data.database.dao.MovieDao;
import com.example.irishka.movieapp.data.database.dao.MovieWithCategoryDao;
import com.example.irishka.movieapp.data.database.dao.RelatedOfMovieDao;
import com.example.irishka.movieapp.data.database.entity.CastDb;
import com.example.irishka.movieapp.data.database.entity.CastOfMovie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CastsDbSource {

    private CastDao castDao;

    private CastOfMovieDao castOfMovieDao;

    @Inject
    public CastsDbSource(CastDao castDao, CastOfMovieDao castOfMovieDao) {
        this.castDao = castDao;
        this.castOfMovieDao = castOfMovieDao;
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

    public Single<List<CastOfMovie>> getMoviesOfCast(long id) {
        return castOfMovieDao.getMoviesOfCast(id);
    }

    public Single<CastDb> getCast(long id) {
        return castDao.getCast(id);
    }

    public void insertAllCoM(List<CastOfMovie> castsOfMovie) {
        castOfMovieDao.trans(castsOfMovie);
    }

}
