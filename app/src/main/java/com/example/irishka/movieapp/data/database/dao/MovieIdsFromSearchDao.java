package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.TypeConverters;

import com.example.irishka.movieapp.data.database.converters.EnumConverter;
import com.example.irishka.movieapp.data.database.entity.MovieIdsFromSearch;
import com.example.irishka.movieapp.data.database.entity.MovieWithCategory;
import com.example.irishka.movieapp.domain.MainType;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class MovieIdsFromSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertMovieId(MovieIdsFromSearch movieIdsFromSearch);

    @Transaction
    public void trans(long movieId, boolean isSearch) {

        if (isSearch) {

            if (getMovieIdsForCount().size() > 4) {
                delete();
            }

            if (getMovie(movieId).size() == 0) {
                insertMovieId(new MovieIdsFromSearch(movieId));
            }
        }
    }

    @Query("SELECT * FROM MovieIdsFromSearch WHERE movieId = :movieId")
    abstract List<MovieIdsFromSearch> getMovie(long movieId);

    @Query("DELETE FROM MovieIdsFromSearch WHERE id = (SELECT MIN(id) FROM MovieIdsFromSearch)")
    abstract void delete();

    @Query("SELECT * FROM MovieIdsFromSearch")
    abstract List<MovieIdsFromSearch> getMovieIdsForCount();

    @Query("SELECT * FROM MovieIdsFromSearch")
    public abstract Single<List<MovieIdsFromSearch>> getMovieIds();

}
