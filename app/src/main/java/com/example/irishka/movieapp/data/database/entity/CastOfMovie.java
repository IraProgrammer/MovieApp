package com.example.irishka.movieapp.data.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CastOfMovie {

    @PrimaryKey(autoGenerate = true)
    public int id;

    long movieId;
    int castId;

    public CastOfMovie(long descriptionId, int genreId){
        this.movieId = descriptionId;
        this.castId = genreId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

}
