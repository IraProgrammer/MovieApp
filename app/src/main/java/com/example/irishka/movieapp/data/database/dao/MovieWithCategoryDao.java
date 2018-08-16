package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.example.irishka.movieapp.data.database.entity.GenreOfMovie;
import com.example.irishka.movieapp.data.database.entity.MovieWithCategory;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class MovieWithCategoryDao {

    @Insert
    abstract void insertMoviesWithCategory(List<MovieWithCategory> moviesWithCategory);

    @Query("SELECT * " +
            "FROM MovieWithCategory " +
            "WHERE type = :type")
    abstract List<MovieWithCategory> getMwCifExist(String type);

    @Transaction
    public void trans(List<MovieWithCategory> moviesWithCategory) {
        List<MovieWithCategory> mWc = getMwCifExist(moviesWithCategory.get(0).getType());

        if (mWc.size() == 0) insertMoviesWithCategory(moviesWithCategory);
    }

    @Query("SELECT * FROM MovieWithCategory WHERE type = :type")
    public abstract Single<List<MovieWithCategory>> getMoviesWithCategory(String type);

}
