package com.example.irishka.movieapp.data.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.irishka.movieapp.data.database.converters.EnumConverter;
import com.example.irishka.movieapp.domain.MainType;

@Entity
public class MovieWithCategory {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @TypeConverters(EnumConverter.class)
    public MainType type;
    public long movieId;

    public MovieWithCategory(MainType type, long movieId){
        this.type = type;
        this.movieId = movieId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public MainType getType() {
        return type;
    }

    public void setType(MainType type) {
        this.type = type;
    }

}
