package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.example.irishka.movieapp.data.database.entity.CastOfMovie;
import com.example.irishka.movieapp.data.database.entity.GenreOfMovie;
import com.example.irishka.movieapp.data.database.entity.RelatedOfMovie;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class CastOfMovieDao {

    @Insert
    public abstract void insertCastOfMovie(List<CastOfMovie> castOfMovie);

    @Query("SELECT * " +
            "FROM CastOfMovie " +
            "WHERE movieId = :movieId")
    public abstract List<CastOfMovie> getCoMifExist(long movieId);

    @Transaction
    public void trans(List<CastOfMovie> castOfMovie) {
        List<CastOfMovie> cOm = getCoMifExist(castOfMovie.get(0).getMovieId());

        if (cOm.size() == 0) {
            insertCastOfMovie(castOfMovie);
        }
    }

    @Query("SELECT * FROM CastOfMovie WHERE movieId = :movieId")
    public abstract Single<List<CastOfMovie>> getCastOfMovie(long movieId);

    @Query("SELECT * FROM CastOfMovie WHERE castId = :castId")
    public abstract Single<List<CastOfMovie>> getMoviesOfCast(long castId);

}
