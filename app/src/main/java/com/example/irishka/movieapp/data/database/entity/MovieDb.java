
package com.example.irishka.movieapp.data.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.irishka.movieapp.data.database.BackdropsConverter;
import com.example.irishka.movieapp.data.database.CountriesConverter;

import java.util.List;

@Entity
public class MovieDb {

    @PrimaryKey
    private long id;

    private String title;

    private String posterUrl;

    private String releaseDate;

    private Boolean adult;

    private double voteAverage;

    private Integer runtime;

    @TypeConverters(CountriesConverter.class)
    private List<ProductionCountryDb> countries;

    @TypeConverters(BackdropsConverter.class)
    private List<BackdropDb> backdrops;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public List<ProductionCountryDb> getCountries() {
        return countries;
    }

    public void setCountries(List<ProductionCountryDb> countries) {
        this.countries = countries;
    }

    public List<BackdropDb> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<BackdropDb> backdrops) {
        this.backdrops = backdrops;
    }

}
