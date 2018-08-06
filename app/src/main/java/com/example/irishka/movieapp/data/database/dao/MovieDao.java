package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.irishka.movieapp.data.database.entity.MovieDb;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<MovieDb> movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieDb movie);

    @Query("SELECT * FROM MovieDb")
    Single<List<MovieDb>> getAllMovies();

//    @Query("SELECT * FROM MovieDb WHERE relatedId = :movieId")
//    Single<List<MovieDb>> getRelatedMovies(long movieId);

    @Query("SELECT * FROM MovieDb WHERE id = :movieId")
    Single<MovieDb> getMovie(long movieId);

}
