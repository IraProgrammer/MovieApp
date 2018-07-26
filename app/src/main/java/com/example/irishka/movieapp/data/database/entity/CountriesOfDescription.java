package com.example.irishka.movieapp.data.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CountriesOfDescription {

    @PrimaryKey(autoGenerate = true)
    public int id;

    long descriptionId;

    public long getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(long descriptionId) {
        this.descriptionId = descriptionId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    String countryName;

    public CountriesOfDescription(long descriptionId, String countryName){
        this.descriptionId = descriptionId;
        this.countryName = countryName;
    }
}