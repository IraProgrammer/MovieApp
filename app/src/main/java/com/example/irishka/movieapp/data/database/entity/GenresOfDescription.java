package com.example.irishka.movieapp.data.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class GenresOfDescription {

    @PrimaryKey(autoGenerate = true)
    public int id;

    long descriptionId;

    public long getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(long descriptionId) {
        this.descriptionId = descriptionId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    int genreId;

    public GenresOfDescription(long descriptionId, int genreId){
        this.descriptionId = descriptionId;
        this.genreId = genreId;
    }

}
