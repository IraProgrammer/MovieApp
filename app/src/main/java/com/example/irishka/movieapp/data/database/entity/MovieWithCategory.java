package com.example.irishka.movieapp.data.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MovieWithCategory {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String type;
    public long movieId;

    public MovieWithCategory(String type, long movieId){
        this.type = type;
        this.movieId = movieId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
