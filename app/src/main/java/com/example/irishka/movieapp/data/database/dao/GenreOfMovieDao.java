package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.example.irishka.movieapp.data.database.entity.GenreOfMovie;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class GenreOfMovieDao {

    @Insert
    public abstract void insertGenreOfMovie(List<GenreOfMovie> genreOfMovies);

    @Query("SELECT * " +
            "FROM GenreOfMovie " +
            "WHERE movieId = :movieId")
    public abstract List<GenreOfMovie> getGoTifExist(long movieId);

    @Transaction
    public void trans(List<GenreOfMovie> genreOfMovies) {
        List<GenreOfMovie> gOm = getGoTifExist(genreOfMovies.get(0).getMovieId());

        if (gOm.size() == 0) insertGenreOfMovie(genreOfMovies);
    }

    @Query("SELECT * FROM GenreOfMovie WHERE movieId = :movieId")
    public abstract Single<List<GenreOfMovie>> getGenresOfMovie(long movieId);

}
