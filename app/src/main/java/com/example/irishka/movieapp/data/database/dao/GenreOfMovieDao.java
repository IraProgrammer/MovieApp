package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.irishka.movieapp.data.database.entity.GenreOfMovie;

import java.util.List;
import java.util.Set;

import io.reactivex.Single;

@Dao
public interface GenreOfMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Set<GenreOfMovie> genresOfMovie);

    @Query("SELECT * FROM GenreOfMovie WHERE movieId = :movieId")
    Single<List<GenreOfMovie>> getGenresOfMovie(long movieId);
}
