package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.irishka.movieapp.data.database.entity.GenresOfDescription;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface GenreOfDescriptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<GenresOfDescription> genresOfDescripton);

    @Query("SELECT * FROM GenresOfDescription WHERE descriptionId = :movieId")
    Single<List<GenresOfDescription>> getGoD(long movieId);
}
