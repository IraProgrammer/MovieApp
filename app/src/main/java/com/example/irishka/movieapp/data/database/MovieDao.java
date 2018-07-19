package com.example.irishka.movieapp.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.irishka.movieapp.data.models.MovieModel;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MovieModel> concreteMovies);

    @Query("SELECT * FROM MovieModel")
    Single<List<MovieModel>> getAllMovies();

}
