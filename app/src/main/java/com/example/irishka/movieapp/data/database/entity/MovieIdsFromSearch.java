package com.example.irishka.movieapp.data.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.irishka.movieapp.data.database.converters.EnumConverter;
import com.example.irishka.movieapp.domain.MainType;

@Entity
public class MovieIdsFromSearch {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public long movieId;

    public MovieIdsFromSearch(long movieId){
        this.movieId = movieId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }
}
