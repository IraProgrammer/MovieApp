package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.irishka.movieapp.data.database.entity.CastOfMovie;
import com.example.irishka.movieapp.data.database.entity.GenreOfMovie;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CastOfMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<CastOfMovie> castsOfMovie);

    @Query("SELECT * FROM CastOfMovie WHERE movieId = :movieId")
    Single<List<CastOfMovie>> getCastsOfMovie(long movieId);

}
