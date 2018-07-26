package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.irishka.movieapp.data.database.entity.DescriptionDb;
import com.example.irishka.movieapp.data.database.entity.MovieDb;
import com.example.irishka.movieapp.domain.entity.Description;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface DescriptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DescriptionDb description);

    @Query("SELECT * FROM DescriptionDb WHERE id = :movieId")
    Single<DescriptionDb> getDescription(long movieId);

}
