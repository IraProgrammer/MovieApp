package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.irishka.movieapp.data.database.entity.BackdropDb;
import com.example.irishka.movieapp.data.database.entity.MovieDb;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface BackdropDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BackdropDb> backdrops);

    @Query("SELECT * FROM BackdropDb")
    Single<List<BackdropDb>> getAllBackdrops();

}