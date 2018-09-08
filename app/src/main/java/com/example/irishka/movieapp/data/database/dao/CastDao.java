package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.irishka.movieapp.data.database.entity.CastDb;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CastDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<CastDb> casts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CastDb cast);

    @Query("SELECT * FROM CastDb WHERE id = :id")
    Single<CastDb> getCast(long id);

}
