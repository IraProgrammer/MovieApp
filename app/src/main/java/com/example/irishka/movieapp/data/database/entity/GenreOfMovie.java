package com.example.irishka.movieapp.data.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class GenreOfMovie {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public long movieId;
    public int genreId;

    public GenreOfMovie(long movieId, int genreId){
        this.movieId = movieId;
        this.genreId = genreId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

}
