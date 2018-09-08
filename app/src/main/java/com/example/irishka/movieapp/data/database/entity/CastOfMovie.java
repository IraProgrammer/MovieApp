package com.example.irishka.movieapp.data.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CastOfMovie {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public long movieId;
    public long castId;

    public CastOfMovie(long movieId, long castId){
        this.movieId = movieId;
        this.castId = castId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public long getCastId() {
        return castId;
    }

    public void setCastId(long castId) {
        this.castId = castId;
    }

}
