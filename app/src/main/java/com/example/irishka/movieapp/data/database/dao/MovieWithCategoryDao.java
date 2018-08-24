package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.TypeConverters;

import com.example.irishka.movieapp.data.database.converters.EnumConverter;
import com.example.irishka.movieapp.data.database.entity.MovieWithCategory;
import com.example.irishka.movieapp.domain.MainType;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class MovieWithCategoryDao {

    @Insert
    abstract void insertMoviesWithCategory(List<MovieWithCategory> moviesWithCategory);

    @TypeConverters(EnumConverter.class)
    @Query("SELECT * " +
            "FROM MovieWithCategory " +
            "WHERE type = :type " +
            "AND movieId = :movieId")
    abstract List<MovieWithCategory> getMwCifExist(MainType type, long movieId);

    @Transaction
    public void trans(List<MovieWithCategory> moviesWithCategory) {
        List<MovieWithCategory> mWc = getMwCifExist(moviesWithCategory.get(0).getType(), moviesWithCategory.get(0).getMovieId());

        if (mWc.size() == 0) insertMoviesWithCategory(moviesWithCategory);
    }

    @TypeConverters(EnumConverter.class)
    @Query("SELECT * FROM MovieWithCategory WHERE type = :type")
    public abstract Single<List<MovieWithCategory>> getMoviesWithCategory(MainType type);

}
