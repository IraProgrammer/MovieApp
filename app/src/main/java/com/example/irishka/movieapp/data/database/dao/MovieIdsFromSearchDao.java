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

    List<Long> movieIds = new ArrayList<>();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertMovieId(MovieIdsFromSearch movieIdsFromSearch);

    @Transaction
    public void trans(long movieId, boolean isSearch) {

        if (isSearch) {

            if (getMovieIdsForCount().size() > 10) {
                deleteTheFirst(getMovie(movieIds.get(0)).get(0));
            }

            if (getMovie(movieId).size() == 0) {

                movieIds.add(movieId);
                insertMovieId(new MovieIdsFromSearch(movieId));
            }
        }
    }

    @Delete
    abstract void deleteTheFirst(MovieIdsFromSearch movieIdsFromSearch);

    @Query("SELECT * FROM MovieIdsFromSearch WHERE movieId = :movieId")
    abstract List<MovieIdsFromSearch> getMovie(long movieId);

    @Query("SELECT * FROM MovieIdsFromSearch")
    abstract List<MovieIdsFromSearch> getMovieIdsForCount();

    @Query("SELECT * FROM MovieIdsFromSearch")
    public abstract Single<List<MovieIdsFromSearch>> getMovieIds();

}
