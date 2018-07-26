package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.irishka.movieapp.data.database.entity.CountriesOfDescription;
import com.example.irishka.movieapp.data.database.entity.GenresOfDescription;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CountriesOfDescriptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<CountriesOfDescription> countriesOfDescripton);

    @Query("SELECT * FROM GenresOfDescription WHERE descriptionId = :movieId")
    Single<List<CountriesOfDescription>> getCoD(long movieId);

}
