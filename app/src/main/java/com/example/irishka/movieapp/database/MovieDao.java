package com.example.irishka.movieapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.irishka.movieapp.model.Pojo.ConcreteMovie;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ConcreteMovie> concreteMovies);

    @Query("SELECT * FROM ConcreteMovie")
    Single<List<ConcreteMovie>> getAllMovies();

}
