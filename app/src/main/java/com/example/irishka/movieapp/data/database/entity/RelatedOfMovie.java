package com.example.irishka.movieapp.data.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

@Entity
public class RelatedOfMovie {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public long movieId;
    public long relatedId;

    public RelatedOfMovie(long movieId, long relatedId){
        this.movieId = movieId;
        this.relatedId = relatedId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(long relatedId) {
        this.relatedId = relatedId;
    }

}
