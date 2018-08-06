
package com.example.irishka.movieapp.data.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.example.irishka.movieapp.data.database.converters.ActorFilmsConverter;
import com.example.irishka.movieapp.data.database.converters.BackdropsConverter;
import com.example.irishka.movieapp.data.database.converters.CountriesConverter;
import com.example.irishka.movieapp.data.database.converters.RelatedConverter;

import java.util.List;

@Entity
public class MovieDb {

    @PrimaryKey
    private long id;

    private long relatedId;

    private String title;

    private String posterUrl;

    private String releaseDate;

    private boolean adult;

    private double voteAverage;

    private int runtime;

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

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    private String overview;

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

    public boolean getAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
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

    public long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(long relatedId) {
        this.relatedId = relatedId;
    }
}
