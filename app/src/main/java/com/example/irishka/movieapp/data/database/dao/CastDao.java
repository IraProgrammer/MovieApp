package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.irishka.movieapp.data.database.entity.BackdropDb;
import com.example.irishka.movieapp.data.database.entity.CastDb;
import com.example.irishka.movieapp.data.database.entity.GenreDb;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CastDb> casts);

    @Query("SELECT * FROM CastDb WHERE castId = :castId")
    Single<CastDb> getCast(int castId);

}
