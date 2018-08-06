package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.example.irishka.movieapp.data.database.entity.GenreOfMovie;
import com.example.irishka.movieapp.data.database.entity.RelatedOfMovie;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class RelatedOfMovieDao {

    @Insert
    public abstract void insertRelatedOfMovie(List<RelatedOfMovie> relatedOfMovie);

    @Query("SELECT * " +
            "FROM RelatedOfMovie " +
            "WHERE movieId = :movieId")
    public abstract List<RelatedOfMovie> getRoMifExist(long movieId);

    @Transaction
    public void trans( List<RelatedOfMovie> relatedOfMovie) {
        List<RelatedOfMovie> rOm = getRoMifExist(relatedOfMovie.get(0).getMovieId());

        if (rOm.size() == 0) insertRelatedOfMovie(relatedOfMovie);
    }

    @Query("SELECT * FROM RelatedOfMovie WHERE movieId = :movieId")
    public abstract Single<List<RelatedOfMovie>> getRelatedOfMovie(long movieId);

}
