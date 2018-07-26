package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.irishka.movieapp.data.database.entity.GenreDb;
import com.example.irishka.movieapp.data.database.entity.ProductionCountryDb;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ProductionCountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ProductionCountryDb> countries);

    @Query("SELECT * FROM ProductionCountryDb WHERE id = :genreId")
    Single<ProductionCountryDb> getCountry(long genreId);

}
